package bootstrap;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.time.DateUtils;

import models.BusBO;
import models.ItineraryBO;
import models.PositionBO;
import models.PositionBO.PositionSense;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

/**
 * @author jgomes - Jefferson Chaves Gomes | 15/09/2014 - 15:18:23
 */
@OnApplicationStart
public class Bootstrap extends Job {

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see play.jobs.Job#doJob()
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public void doJob() throws Exception {
        Logger.info("======================================================");
        Logger.info("BOOTSTRAP START");
        Logger.info("======================================================");
        try {
//            this.createInitialData();
        } catch (final Exception e) {
            Logger.error("======================================================");
            Logger.error("BOOTSTRAP FAIL");
            Logger.error(e, e.getLocalizedMessage());
            Logger.error("======================================================");
        }
        Logger.info("======================================================");
        Logger.info("BOOTSTRAP FINISHED");
        Logger.info("======================================================");
    }

    private void createInitialData() throws InterruptedException {
        Logger.info("Creating initial data...");
        // Bus - 1
        ItineraryBO itineraty = ItineraryBO.findAndSave("560", "Riacho Fundo II", "W3 Sul");
        BusBO bus = BusBO.findAndSave("JFJ-1593", 77775555L, (short)57, itineraty);
        this.saveRandonPositions(bus);
        // Bus - 2
        itineraty = ItineraryBO.findAndSave("813.1", "Pistão Sul", "W3 Sul");
        bus = BusBO.findAndSave("OVP-5577", 75757575L, (short)75, itineraty);
        this.saveRandonPositions(bus);
        Logger.info("Initial data was created...");
    }

    private void saveRandonPositions(final BusBO bus) throws InterruptedException {
        double latitude = -15.794133;
        double longitude = -47.890502;
        for (int i = 0; i < 10; i++, latitude -= 0.1, longitude -= 0.1) {
            final PositionBO object = new PositionBO();
            object.setLatitude(new BigDecimal(latitude).setScale(6, BigDecimal.ROUND_CEILING).doubleValue());
            object.setLongitude(new BigDecimal(longitude).setScale(6, BigDecimal.ROUND_CEILING).doubleValue());
            object.setDate(DateUtils.addHours(new Date(), -3));
            object.setSpeed((short) new Random().nextInt(100));
            object.setPositionSense(PositionSense.TO_END_POINT);
            object.setBus(bus);
            object.save();
            Thread.sleep(1000);
        }
    }
}
