package cz.vutbr.fit.simulatormanager;

public class Constants {
    // path is relative to WebContent
    public static final String CONFIGS_PATH = "/WEB-INF/simulatorManager.prop";
    public static final int MAX_ENGINES_NUM = 8;

    // default values of new engine model
    public static final boolean RPM = false;
    public static final float MINRPM = 0.0f;
    public static final float LOWRPM = 0.0f;
    public static final float HIGHRPM = 0.0f;
    public static final float MAXRPM = 0.0f;

    public static final boolean PWR = false;
    public static final float MINPWR = 0.0f;
    public static final float LOWPWR = 0.0f;
    public static final float HIGHPWR = 0.0f;
    public static final float MAXPWR = 0.0f;

    public static final boolean PWP = true;
    public static final float MINPWP = 0.0f;
    public static final float LOWPWP = 10.0f;
    public static final float HIGHPWP = 90.0f;
    public static final float MAXPWP = 100.0f;

    public static final boolean MP = false;
    public static final float MINMP = 0.0f;
    public static final float LOWMP = 0.0f;
    public static final float HIGHMP = 0.0f;
    public static final float MAXMP = 0.0f;

    public static final boolean EGT1 = true;
    public static final float MINEGT1 = 60.0f;
    public static final float LOWEGT1 = 60.0f;
    public static final float HIGHEGT1 = 850.0f;
    public static final float MAXEGT1 = 950.0f;

    public static final boolean EGT2 = false;
    public static final float MINEGT2 = 0.0f;
    public static final float LOWEGT2 = 0.0f;
    public static final float HIGHEGT2 = 0.0f;
    public static final float MAXEGT2 = 0.0f;

    public static final boolean CHT1 = true;
    public static final float MINCHT1 = 0.0f;
    public static final float LOWCHT1 = 60.0f;
    public static final float HIGHCHT1 = 300.0f;
    public static final float MAXCHT1 = 500.0f;

    public static final boolean CHT2 = false;
    public static final float MINCHT2 = 0.0f;
    public static final float LOWCHT2 = 0.0f;
    public static final float HIGHCHT2 = 0.0f;
    public static final float MAXCHT2 = 0.0f;

    public static final boolean EST = true;
    public static final float MINEST = 0.0f;
    public static final float LOWEST = 60.0f;
    public static final float HIGHEST = 300.0f;
    public static final float MAXEST = 500.0f;

    public static final boolean FF = true;
    public static final float MINFF = 0.0f;
    public static final float LOWFF = 60.0f;
    public static final float HIGHFF = 300.0f;
    public static final float MAXFF = 500.0f;

    public static final boolean FP = true;
    public static final float MINFP = 0.0f;
    public static final float LOWFP = 34000.0f;
    public static final float HIGHFP = 80000.0f;
    public static final float MAXFP = 100000.0f;

    public static final boolean OP = true;
    public static final float MINOP = 0.0f;
    public static final float LOWOP = 90000.0f;
    public static final float HIGHOP = 280000.0f;
    public static final float MAXOP = 300000.0f;

    public static final boolean OT = true;
    public static final float MINOT = 0.0f;
    public static final float LOWOT = 54.0f;
    public static final float HIGHOT = 140.0f;
    public static final float MAXOT = 155.0f;

    public static final boolean N1 = true;
    public static final float MINN1 = 0.0f;
    public static final float LOWN1 = 20.0f;
    public static final float HIGHN1 = 90.0f;
    public static final float MAXN1 = 104.0f;

    public static final boolean N2 = true;
    public static final float MINN2 = 0.0f;
    public static final float LOWN2 = 20.0f;
    public static final float HIGHN2 = 95.0f;
    public static final float MAXN2 = 105.0f;

    public static final boolean VIB = true;
    public static final float MINVIB = 0.0f;
    public static final float LOWVIB = 60.0f;
    public static final float HIGHVIB = 300.0f;
    public static final float MAXVIB = 500.0f;

    public static final boolean VLT = true;
    public static final float MINVLT = 0.0f;
    public static final float LOWVLT = 60.0f;
    public static final float HIGHVLT = 300.0f;
    public static final float MAXVLT = 500.0f;

    public static final boolean AMP = true;
    public static final float MINAMP = 0.0f;
    public static final float LOWAMP = 60.0f;
    public static final float HIGHAMP = 300.0f;
    public static final float MAXAMP = 500.0f;

    public static final boolean LFU = true;
    public static final float MINLFU = 0.0f;
    public static final float LOWLFU = 500.0f;
    public static final float HIGHLFU = 380.0f;
    public static final float MAXLFU = 3915.0f;

    public static final boolean CFU = true;
    public static final float MINCFU = 0.0f;
    public static final float LOWCFU = 907.0f;
    public static final float HIGHCFU = 12700.0f;
    public static final float MAXCFU = 13066.0f;

    public static final boolean RFU = true;
    public static final float MINRFU = 0.0f;
    public static final float LOWRFU = 500.0f;
    public static final float HIGHRFU = 3800.0f;
    public static final float MAXRFU = 3915.0f;

}
