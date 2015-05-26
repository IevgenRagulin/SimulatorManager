package cz.vutbr.fit.simulatormanager.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;

/**
 * Class for accessing Resources (images, files etc.)
 * 
 * @author zhenia
 *
 */
public class ResourceUtil {
    final static Logger LOG = LoggerFactory.getLogger(ResourceUtil.class);
    private final static String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
    private final static Resource plusImg = new FileResource(new File(basepath + "/WEB-INF/images/button_images/plus.png"));
    private final static Resource minusImg = new FileResource(new File(basepath + "/WEB-INF/images/button_images/minus.png"));
    private final static Resource pingImg = new FileResource(new File(basepath + "/WEB-INF/images/button_images/ping.png"));
    private final static Resource cleanImg = new FileResource(new File(basepath + "/WEB-INF/images/button_images/clean.png"));
    private final static Resource startImg = new FileResource(new File(basepath + "/WEB-INF/images/button_images/start.png"));
    private final static Resource settingsImg = new FileResource(
	    new File(basepath + "/WEB-INF/images/button_images/settings.png"));
    private final static Resource ev97Img = new FileResource(new File(basepath + "/WEB-INF/images/ev97.jpg"));
    private final static Resource ev97MainImg = new FileResource(new File(basepath + "/WEB-INF/images/ev97_main.jpg"));
    private final static Resource boeing737Img = new FileResource(new File(basepath + "/WEB-INF/images/boeing737.jpg"));
    private final static Resource boeing737SquareImg = new FileResource(new File(basepath
	    + "/WEB-INF/images/boeing737_square.jpg"));
    private final static Resource simulatorManagerImg = new FileResource(new File(basepath
	    + "/WEB-INF/images/simulatorManager.jpg"));
    private final static Resource configurationMainImg = new FileResource(new File(basepath
	    + "/WEB-INF/images/configuration-main.jpg"));
    private final static Resource saveImg = new FileResource(new File(basepath + "/WEB-INF/images/button_images/save.png"));
    private final static File applicationUsageInfo = new File(basepath + "/WEB-INF/applicationUsageInfo.html");

    public static Resource getPlusImgResource() {
	return plusImg;
    }

    public static Resource getMinusImgResource() {
	return minusImg;
    }

    public static Resource getPingImg() {
	return pingImg;
    }

    public static Resource getCleanImg() {
	return cleanImg;
    }

    public static Resource getStartImg() {
	return startImg;
    }

    public static Resource getSettingsImg() {
	return settingsImg;
    }

    public static Resource getEv97Img() {
	return ev97Img;
    }

    public static Resource getEv97MainImg() {
	return ev97MainImg;
    }

    public static Resource getBoeing737Img() {
	return boeing737Img;
    }

    public static Resource getBoeing737SquareImg() {
	return boeing737SquareImg;
    }

    public static Resource getSimulatorManagerImg() {
	return simulatorManagerImg;
    }

    public static Resource getConfigurationMainImg() {
	return configurationMainImg;
    }

    public static String getApplicationUsageInfo() {
	LOG.info("Going to read application usage info from file");
	StringBuilder usageInfoStr = new StringBuilder();
	try {
	    Files.lines(applicationUsageInfo.toPath()).forEach(s -> usageInfoStr.append(s));
	    String usageInfo = usageInfoStr.toString();
	    LOG.info("Read application usage info from file: {}", usageInfo);
	    return usageInfo;
	} catch (IOException e) {
	    LOG.error("Error reading application usage info from file", e);
	    throw new RuntimeException("Couldn't read application usage info file ", e);
	}

    }

    public static Resource getSaveImgResource() {
	return saveImg;
    }
}
