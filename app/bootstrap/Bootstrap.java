package bootstrap;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import models.IntinerarioBO;
import models.OnibusBO;
import models.PosicaoBO;
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
            this.createInitialData();
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
        IntinerarioBO itineraty = this.findItineraty("560", "Riacho", "W3 Sul");
        OnibusBO bus = this.findBus("JFJ1593", 77775555L, itineraty);
        this.saveRandonPositions(bus);
        // Bus - 2
        itineraty = this.findItineraty("813.1", "W3 Sul", "Pistão Sul");
        bus = this.findBus("OVP5577", 75757575L, itineraty);
        this.saveRandonPositions(bus);
        Logger.info("Initial data was created...");
    }
    private IntinerarioBO findItineraty(final String itineratyId, final String source, final String target) {
        IntinerarioBO object = IntinerarioBO.findById(itineratyId);
        if (object == null) {
            object = new IntinerarioBO(itineratyId, source, target);
            object.save();
        }
        return object;
    }
    private OnibusBO findBus(final String busId, final Long busNumber, final IntinerarioBO itineraty) {
        OnibusBO object = OnibusBO.findById(busId);
        if (object == null) {
            object = new OnibusBO(busId, busNumber, (short) 57, itineraty);
            object.save();
        }
        return object;
    }
    private void saveRandonPositions(final OnibusBO bus) throws InterruptedException {
        double latitude = -15.794133;
        double longitude = -47.890502;
        for (int i = 0; i < 10; i++, latitude -= 0.1, longitude -= 0.1) {
            final PosicaoBO object = new PosicaoBO();
            object.setLatitude(new BigDecimal(latitude).setScale(6, BigDecimal.ROUND_CEILING).doubleValue());
            object.setLongitude(new BigDecimal(longitude).setScale(6, BigDecimal.ROUND_CEILING).doubleValue());
            object.setData(new Date());
            object.setVelocidade((short) new Random().nextInt(100));
            object.setOnibus(bus);
            object.save();
            // Thread.sleep(1000);
        }
    }
}
