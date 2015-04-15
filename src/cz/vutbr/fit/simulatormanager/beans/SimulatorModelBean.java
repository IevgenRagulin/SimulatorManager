package cz.vutbr.fit.simulatormanager.beans;

/**
 * Bean corresponding to simulatormodel table in database
 * 
 * @author zhenia
 *
 */
public class SimulatorModelBean {

    private Integer minspeed;
    private Integer maxspeed;
    private Integer maxspeedonflaps;
    private Integer minspeedonflaps;
    private Boolean hasgears;
    private Integer numberoflandinggears;
    private Boolean lfu;
    private Float minlfu;
    private Float maxlfu;
    private Boolean rfu;
    private Float minrfu;
    private Float maxrfu;
    private Boolean cfu;
    private Float mincfu;
    private Float maxcfu;

    public SimulatorModelBean() {

    }

    public Integer getMinspeed() {
	return minspeed;
    }

    public void setMinspeed(Integer minspeed) {
	this.minspeed = minspeed;
    }

    public Integer getMaxspeed() {
	return maxspeed;
    }

    public void setMaxspeed(Integer maxpeed) {
	this.maxspeed = maxpeed;
    }

    public Integer getMaxspeedonflaps() {
	return maxspeedonflaps;
    }

    public void setMaxspeedonflaps(Integer maxspeedonflaps) {
	this.maxspeedonflaps = maxspeedonflaps;
    }

    public Integer getMinspeedonflaps() {
	return minspeedonflaps;
    }

    public void setMinspeedonflaps(Integer minspeedonflaps) {
	this.minspeedonflaps = minspeedonflaps;
    }

    public Boolean isHasgears() {
	return hasgears;
    }

    public void setHasgears(Boolean hasgears) {
	this.hasgears = hasgears;
    }

    public Integer getNumberoflandinggears() {
	return numberoflandinggears;
    }

    public void setNumberoflandinggears(Integer numberoflandinggears) {
	this.numberoflandinggears = numberoflandinggears;
    }

    public Boolean isLfu() {
	return lfu;
    }

    public void setLfu(Boolean lfu) {
	this.lfu = lfu;
    }

    public Float getMinlfu() {
	return minlfu;
    }

    public void setMinlfu(Float minlfu) {
	this.minlfu = minlfu;
    }

    public Float getMaxlfu() {
	return maxlfu;
    }

    public void setMaxlfu(Float maxlfu) {
	this.maxlfu = maxlfu;
    }

    public Boolean isRfu() {
	return rfu;
    }

    public void setRfu(Boolean rfu) {
	this.rfu = rfu;
    }

    public Float getMinrfu() {
	return minrfu;
    }

    public void setMinrfu(Float minrfu) {
	this.minrfu = minrfu;
    }

    public Float getMaxrfu() {
	return maxrfu;
    }

    public void setMaxrfu(Float maxrfu) {
	this.maxrfu = maxrfu;
    }

    public Boolean isCfu() {
	return cfu;
    }

    public void setCfu(Boolean cfu) {
	this.cfu = cfu;
    }

    public Float getMincfu() {
	return mincfu;
    }

    public void setMincfu(Float mincfu) {
	this.mincfu = mincfu;
    }

    public Float getMaxcfu() {
	return maxcfu;
    }

    public void setMaxcfu(Float maxcfu) {
	this.maxcfu = maxcfu;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((cfu == null) ? 0 : cfu.hashCode());
	result = prime * result + ((hasgears == null) ? 0 : hasgears.hashCode());
	result = prime * result + ((lfu == null) ? 0 : lfu.hashCode());
	result = prime * result + ((maxcfu == null) ? 0 : maxcfu.hashCode());
	result = prime * result + ((maxlfu == null) ? 0 : maxlfu.hashCode());
	result = prime * result + ((maxrfu == null) ? 0 : maxrfu.hashCode());
	result = prime * result + ((maxspeed == null) ? 0 : maxspeed.hashCode());
	result = prime * result + ((maxspeedonflaps == null) ? 0 : maxspeedonflaps.hashCode());
	result = prime * result + ((mincfu == null) ? 0 : mincfu.hashCode());
	result = prime * result + ((minlfu == null) ? 0 : minlfu.hashCode());
	result = prime * result + ((minrfu == null) ? 0 : minrfu.hashCode());
	result = prime * result + ((minspeed == null) ? 0 : minspeed.hashCode());
	result = prime * result + ((minspeedonflaps == null) ? 0 : minspeedonflaps.hashCode());
	result = prime * result + ((numberoflandinggears == null) ? 0 : numberoflandinggears.hashCode());
	result = prime * result + ((rfu == null) ? 0 : rfu.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	SimulatorModelBean other = (SimulatorModelBean) obj;
	if (cfu == null) {
	    if (other.cfu != null)
		return false;
	} else if (!cfu.equals(other.cfu))
	    return false;
	if (hasgears == null) {
	    if (other.hasgears != null)
		return false;
	} else if (!hasgears.equals(other.hasgears))
	    return false;
	if (lfu == null) {
	    if (other.lfu != null)
		return false;
	} else if (!lfu.equals(other.lfu))
	    return false;
	if (maxcfu == null) {
	    if (other.maxcfu != null)
		return false;
	} else if (!maxcfu.equals(other.maxcfu))
	    return false;
	if (maxlfu == null) {
	    if (other.maxlfu != null)
		return false;
	} else if (!maxlfu.equals(other.maxlfu))
	    return false;
	if (maxrfu == null) {
	    if (other.maxrfu != null)
		return false;
	} else if (!maxrfu.equals(other.maxrfu))
	    return false;
	if (maxspeed == null) {
	    if (other.maxspeed != null)
		return false;
	} else if (!maxspeed.equals(other.maxspeed))
	    return false;
	if (maxspeedonflaps == null) {
	    if (other.maxspeedonflaps != null)
		return false;
	} else if (!maxspeedonflaps.equals(other.maxspeedonflaps))
	    return false;
	if (mincfu == null) {
	    if (other.mincfu != null)
		return false;
	} else if (!mincfu.equals(other.mincfu))
	    return false;
	if (minlfu == null) {
	    if (other.minlfu != null)
		return false;
	} else if (!minlfu.equals(other.minlfu))
	    return false;
	if (minrfu == null) {
	    if (other.minrfu != null)
		return false;
	} else if (!minrfu.equals(other.minrfu))
	    return false;
	if (minspeed == null) {
	    if (other.minspeed != null)
		return false;
	} else if (!minspeed.equals(other.minspeed))
	    return false;
	if (minspeedonflaps == null) {
	    if (other.minspeedonflaps != null)
		return false;
	} else if (!minspeedonflaps.equals(other.minspeedonflaps))
	    return false;
	if (numberoflandinggears == null) {
	    if (other.numberoflandinggears != null)
		return false;
	} else if (!numberoflandinggears.equals(other.numberoflandinggears))
	    return false;
	if (rfu == null) {
	    if (other.rfu != null)
		return false;
	} else if (!rfu.equals(other.rfu))
	    return false;
	return true;
    }

}
