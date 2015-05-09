package cz.vutbr.fit.simulatormanager.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.vutbr.fit.simulatormanager.items.SimulationInfoItem;

/**
 * Class for measuring distance between cordinates
 * 
 * @author zhenia
 *
 */
public class DistanceUtil {
    private final static Logger LOG = LoggerFactory.getLogger(DistanceUtil.class);

    /**
     * calculates distance between two points in meters
     * 
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static double distanceBetweenTwoPoints(double lat1, double lon1, double lat2, double lon2) {
	if ((lat1 == lat2) && (lon1 == lon2)) {
	    return 0.0;
	}
	double theta = lon1 - lon2;
	double dist = Math.sin(DistanceUtil.deg2rad(lat1)) * Math.sin(DistanceUtil.deg2rad(lat2))
		+ Math.cos(DistanceUtil.deg2rad(lat1)) * Math.cos(DistanceUtil.deg2rad(lat2))
		* Math.cos(DistanceUtil.deg2rad(theta));
	dist = Math.acos(dist);
	dist = DistanceUtil.rad2deg(dist);
	dist = dist * 60 * 1.1515;
	dist = dist * 1.609344 * 1000;
	LOG.debug("Measured distance between lat1: {}, lon1: {}, lat2: {}, lon2: {}. Result: {}", lat1, lon1, lat2, lon2, dist);
	return dist;
    }

    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    /* :: This function converts decimal degrees to radians : */
    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    public static double deg2rad(double deg) {
	return (deg * Math.PI / 180.0);
    }

    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    /* :: This function converts radians to decimal degrees : */
    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    public static double rad2deg(double rad) {
	return (rad * 180 / Math.PI);
    }

    /**
     * Returns true if the plane has moved n a distance more then @distInMeters
     * Returns false if the plane has not moved too much.
     * 
     * If both previous and current simulations are false, return false
     * 
     * If there is no information about previous simulations in db, but there is
     * information about new simulation then we return true
     */
    public static boolean hasPlaneMovedMoreThan(SimulationInfoItem currentSimItem, SimulationInfoItem prevSimItem,
	    double distInMetersThreshold) {
	boolean hasPlaneMoved = false;
	double distanceActual = 0.0;
	if (prevSimItem != null) {
	    Double prevLatitude = prevSimItem.getBean().getLatitude();
	    Double prevLongtitude = prevSimItem.getBean().getLongtitude();
	    Double currentLatitude = currentSimItem.getBean().getLatitude();
	    Double currentLongtitude = currentSimItem.getBean().getLongtitude();
	    distanceActual = distanceBetweenTwoPoints(prevLatitude, prevLongtitude, currentLatitude, currentLongtitude);
	    if (distanceActual >= distInMetersThreshold) {
		hasPlaneMoved = true;
	    }
	} else if (prevSimItem == null && currentSimItem == null) {
	    hasPlaneMoved = false;
	} else if (prevSimItem == null && currentSimItem != null) {
	    hasPlaneMoved = true;
	}
	if (hasPlaneMoved) {
	    LOG.debug(
		    "Plane has moved or there is no info about simulations for simulator. Has moved over: {} meters. The threshold is: {}. PrevSimItem: {}",
		    distanceActual, distInMetersThreshold, prevSimItem);
	}
	return hasPlaneMoved;
    }

}
