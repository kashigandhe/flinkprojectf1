package pojo;

import java.util.Objects;

public class Position {
    private String busiDate;
    private Long posnId;
    private Long acctId;
    private String positionTypeCode;
    private Long viId;
    private Double closePrice;
    private Double unitOfTrade;
    private Double grossLngQty;
    private Double unsegLngQty;
    private Double grossShtQty;
    private Long cmoId;
    private String cmoName;
    private String fininstrSymb;
    private String cusip;
    private String putCallCode;
    private Double strkPrce;
    private String expDate;
    private String rskGrpSymb;
    private String rskFactSymb;


    public Position(String busiDate, Long posnId, Long acctId, String positionTypeCode, Long viId, Double closePrice, Double unitOfTrade, Double grossLngQty, Double unsegLngQty, Double grossShtQty, Long cmoId, String cmoName, String fininstrSymb, String cusip, String putCallCode, Double strkPrce, String expDate, String rskGrpSymb, String rskFactSymb) {
        this.busiDate = busiDate;
        this.posnId = posnId;
        this.acctId = acctId;
        this.positionTypeCode = positionTypeCode;
        this.viId = viId;
        this.closePrice = closePrice;
        this.unitOfTrade = unitOfTrade;
        this.grossLngQty = grossLngQty;
        this.unsegLngQty = unsegLngQty;
        this.grossShtQty = grossShtQty;
        this.cmoId = cmoId;
        this.cmoName = cmoName;
        this.fininstrSymb = fininstrSymb;
        this.cusip = cusip;
        this.putCallCode = putCallCode;
        this.strkPrce = strkPrce;
        this.expDate = expDate;
        this.rskGrpSymb = rskGrpSymb;
        this.rskFactSymb = rskFactSymb;
    }

    public String getBusiDate() {
        return busiDate;
    }

    public void setBusiDate(String busiDate) {
        this.busiDate = busiDate;
    }

    public Long getPosnId() {
        return posnId;
    }

    public void setPosnId(Long posnId) {
        this.posnId = posnId;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public String getPositionTypeCode() {
        return positionTypeCode;
    }

    public void setPositionTypeCode(String positionTypeCode) {
        this.positionTypeCode = positionTypeCode;
    }

    public Long getViId() {
        return viId;
    }

    public void setViId(Long viId) {
        this.viId = viId;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Double getUnitOfTrade() {
        return unitOfTrade;
    }

    public void setUnitOfTrade(Double unitOfTrade) {
        this.unitOfTrade = unitOfTrade;
    }

    public Double getGrossLngQty() {
        return grossLngQty;
    }

    public void setGrossLngQty(Double grossLngQty) {
        this.grossLngQty = grossLngQty;
    }

    public Double getUnsegLngQty() {
        return unsegLngQty;
    }

    public void setUnsegLngQty(Double unsegLngQty) {
        this.unsegLngQty = unsegLngQty;
    }

    public Double getGrossShtQty() {
        return grossShtQty;
    }

    public void setGrossShtQty(Double grossShtQty) {
        this.grossShtQty = grossShtQty;
    }

    public Long getCmoId() {
        return cmoId;
    }

    public void setCmoId(Long cmoId) {
        this.cmoId = cmoId;
    }

    public String getCmoName() {
        return cmoName;
    }

    public void setCmoName(String cmoName) {
        this.cmoName = cmoName;
    }

    public String getFininstrSymb() {
        return fininstrSymb;
    }

    public void setFininstrSymb(String fininstrSymb) {
        this.fininstrSymb = fininstrSymb;
    }

    public String getCusip() {
        return cusip;
    }

    public void setCusip(String cusip) {
        this.cusip = cusip;
    }

    public String getPutCallCode() {
        return putCallCode;
    }

    public void setPutCallCode(String putCallCode) {
        this.putCallCode = putCallCode;
    }

    public Double getStrkPrce() {
        return strkPrce;
    }

    public void setStrkPrce(Double strkPrce) {
        this.strkPrce = strkPrce;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getRskGrpSymb() {
        return rskGrpSymb;
    }

    public void setRskGrpSymb(String rskGrpSymb) {
        this.rskGrpSymb = rskGrpSymb;
    }

    public String getRskFactSymb() {
        return rskFactSymb;
    }

    public void setRskFactSymb(String rskFactSymb) {
        this.rskFactSymb = rskFactSymb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return busiDate.equals(position.busiDate) &&
                posnId.equals(position.posnId) &&
                Objects.equals(acctId, position.acctId) &&
                Objects.equals(positionTypeCode, position.positionTypeCode) &&
                Objects.equals(viId, position.viId) &&
                Objects.equals(closePrice, position.closePrice) &&
                Objects.equals(unitOfTrade, position.unitOfTrade) &&
                Objects.equals(grossLngQty, position.grossLngQty) &&
                Objects.equals(unsegLngQty, position.unsegLngQty) &&
                Objects.equals(grossShtQty, position.grossShtQty) &&
                Objects.equals(cmoId, position.cmoId) &&
                Objects.equals(cmoName, position.cmoName) &&
                Objects.equals(fininstrSymb, position.fininstrSymb) &&
                Objects.equals(cusip, position.cusip) &&
                Objects.equals(putCallCode, position.putCallCode) &&
                Objects.equals(strkPrce, position.strkPrce) &&
                Objects.equals(expDate, position.expDate) &&
                Objects.equals(rskGrpSymb, position.rskGrpSymb) &&
                Objects.equals(rskFactSymb, position.rskFactSymb);
    }

    @Override
    public int hashCode() {
        return Objects.hash(busiDate, posnId, acctId, positionTypeCode, viId, closePrice, unitOfTrade, grossLngQty, unsegLngQty, grossShtQty, cmoId, cmoName, fininstrSymb, cusip, putCallCode, strkPrce, expDate, rskGrpSymb, rskFactSymb);
    }
}
