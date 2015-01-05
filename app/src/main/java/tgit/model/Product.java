package tgit.model;

/**
 * Created by liulixiang on 2015/1/5.
 * Description: 产品信息
 */
public class Product {
    private String privateId;

    public final String getId() {
        return privateId;
    }

    public final void setId(String value) {
        privateId = value;
    }

    private String privateOrgCode;

    public final String getOrgCode() {
        return privateOrgCode;
    }

    public final void setOrgCode(String value) {
        privateOrgCode = value;
    }

    /**
     * 工序编码
     */
    private String privateWorkshopCode;

    public final String getWorkshopCode() {
        return privateWorkshopCode;
    }

    public final void setWorkshopCode(String value) {
        privateWorkshopCode = value;
    }


    /**
     * 工序名称
     */
    private String privateWorkshopName;

    public final String getWorkshopName() {
        return privateWorkshopName;
    }

    public final void setWorkshopName(String value) {
        privateWorkshopName = value;
    }

    /**
     * 产品编号
     */
    private String privateProductNo;

    public final String getProductNo() {
        return privateProductNo;
    }

    public final void setProductNo(String value) {
        privateProductNo = value;
    }

    /**
     * 批次编号
     */
    private String privateBatchNumber;

    public final String getBatchNumber() {
        return privateBatchNumber;
    }

    public final void setBatchNumber(String value) {
        privateBatchNumber = value;
    }

    /**
     * 物料编码
     */
    private String privateMaterialNumber;

    public final String getMaterialNumber() {
        return privateMaterialNumber;
    }

    public final void setMaterialNumber(String value) {
        privateMaterialNumber = value;
    }

    /**
     * 产品名称
     */
    private String privateProductName;

    public final String getProductName() {
        return privateProductName;
    }

    public final void setProductName(String value) {
        privateProductName = value;
    }

    /**
     * 毛重
     */
    private String privateGross;

    public final String getGross() {
        return privateGross;
    }

    public final void setGross(String value) {
        privateGross = value;
    }

    /**
     * 皮重
     */
    private String privateTare;

    public final String getTare() {
        return privateTare;
    }

    public final void setTare(String value) {
        privateTare = value;
    }

    /**
     * 净重
     */
    private String privateSuttle;

    public final String getSuttle() {
        return privateSuttle;
    }

    public final void setSuttle(String value) {
        privateSuttle = value;
    }

    /**
     * 货位编号
     */
    private String privateInLocatorId;

    public final String getInLocatorId() {
        return privateInLocatorId;
    }

    public final void setInLocatorId(String value) {
        privateInLocatorId = value;
    }

    /**
     * 货位编号
     */
    private String privateInLocatorCode;

    public final String getInLocatorCode() {
        return privateInLocatorCode;
    }

    public final void setInLocatorCode(String value) {
        privateInLocatorCode = value;
    }

    /**
     * 货位
     */
    private String privateInLocator;

    public final String getInLocator() {
        return privateInLocator;
    }

    public final void setInLocator(String value) {
        privateInLocator = value;
    }

    /**
     * 状态
     */
    private String privateTemp;

    public final String getTemp() {
        return privateTemp;
    }

    public final void setTemp(String value) {
        privateTemp = value;
    }

    /**
     * 型号
     */
    private String privateModel;

    public final String getModel() {
        return privateModel;
    }

    public final void setModel(String value) {
        privateModel = value;
    }

    /**
     * 规格
     */
    private String privateSpecification;

    public final String getSpecification() {
        return privateSpecification;
    }

    public final void setSpecification(String value) {
        privateSpecification = value;
    }

    /**
     * 产品日期
     */
    private String privateProductDate;

    public final String getProductDate() {
        return privateProductDate;
    }

    public final void setProductDate(String value) {
        privateProductDate = value;
    }

    private String privateInSubinventoryId;

    public final String getInSubinventoryId() {
        return privateInSubinventoryId;
    }

    public final void setInSubinventoryId(String value) {
        privateInSubinventoryId = value;
    }

    private String privateInSubinventory;

    public final String getInSubinventory() {
        return privateInSubinventory;
    }

    public final void setInSubinventory(String value) {
        privateInSubinventory = value;
    }

    private String privateBussinessId;

    public final String getBussinessId() {
        return privateBussinessId;
    }

    public final void setBussinessId(String value) {
        privateBussinessId = value;
    }

    /**
     * 拆包前ID
     */
    private String privateOriginId;

    public final String getOriginId() {
        return privateOriginId;
    }

    public final void setOriginId(String value) {
        privateOriginId = value;
    }

    /**
     * 拆包前产品编号
     */
    private String privateOriginProductNo;

    public final String getOriginProductNo() {
        return privateOriginProductNo;
    }

    public final void setOriginProductNo(String value) {
        privateOriginProductNo = value;
    }

    /**
     * 是否拆包，如果拆包了则不能使用
     */
    private Boolean privateIsSplitted;

    public final Boolean getIsSplitted() {
        return privateIsSplitted;
    }

    public final void setIsSplitted(Boolean value) {
        privateIsSplitted = value;
    }

    /**
     * 是否发运
     */
    private Boolean privateIsDelivered;

    public final Boolean getIsDelivered() {
        return privateIsDelivered;
    }

    public final void setIsDelivered(Boolean value) {
        privateIsDelivered = value;
    }

    /**
     * 发运时间
     */
    private java.util.Date privateDeliveryTime;

    public final java.util.Date getDeliveryTime() {
        return privateDeliveryTime;
    }

    public final void setDeliveryTime(java.util.Date value) {
        privateDeliveryTime = value;
    }

    /**
     * 是否被质计系统处理
     */
    private Integer privateIsHandledByZJ;

    public final Integer getIsHandledByZJ() {
        return privateIsHandledByZJ;
    }

    public final void setIsHandledByZJ(Integer value) {
        privateIsHandledByZJ = value;
    }

    /**
     * 质计处理时间
     */
    private java.util.Date privateHandleTime;

    public final java.util.Date getHandleTime() {
        return privateHandleTime;
    }

    public final void setHandleTime(java.util.Date value) {
        privateHandleTime = value;
    }

    /**
     * 是否打印
     */
    private Boolean privateIsPrinted;

    public final Boolean getIsPrinted() {
        return privateIsPrinted;
    }

    public final void setIsPrinted(Boolean value) {
        privateIsPrinted = value;
    }

    /**
     * 打印次数
     */
    private Integer privatePrintedTimes;

    public final Integer getPrintedTimes() {
        return privatePrintedTimes;
    }

    public final void setPrintedTimes(Integer value) {
        privatePrintedTimes = value;
    }

    /**
     * 打印时间
     */
    private java.util.Date privateLastPrintedTime;

    public final java.util.Date getLastPrintedTime() {
        return privateLastPrintedTime;
    }

    public final void setLastPrintedTime(java.util.Date value) {
        privateLastPrintedTime = value;
    }

    /**
     * 创建时间
     */
    private java.util.Date privateCreateTime;

    public final java.util.Date getCreateTime() {
        return privateCreateTime;
    }

    public final void setCreateTime(java.util.Date value) {
        privateCreateTime = value;
    }

    private String privateCreateUserId;

    public final String getCreateUserId() {
        return privateCreateUserId;
    }

    public final void setCreateUserId(String value) {
        privateCreateUserId = value;
    }

    private String privateCreateUserName;

    public final String getCreateUserName() {
        return privateCreateUserName;
    }

    public final void setCreateUserName(String value) {
        privateCreateUserName = value;
    }

    private java.util.Date privateLastUpdateTime;

    public final java.util.Date getLastUpdateTime() {
        return privateLastUpdateTime;
    }

    public final void setLastUpdateTime(java.util.Date value) {
        privateLastUpdateTime = value;
    }

    private String privateLastUpdateUserId;

    public final String getLastUpdateUserId() {
        return privateLastUpdateUserId;
    }

    public final void setLastUpdateUserId(String value) {
        privateLastUpdateUserId = value;
    }

    private String privateLastUpdateUserName;

    public final String getLastUpdateUserName() {
        return privateLastUpdateUserName;
    }

    public final void setLastUpdateUserName(String value) {
        privateLastUpdateUserName = value;
    }

    private String privateAttribute1;

    public final String getAttribute1() {
        return privateAttribute1;
    }

    public final void setAttribute1(String value) {
        privateAttribute1 = value;
    }

    private String privateAttribute2;

    public final String getAttribute2() {
        return privateAttribute2;
    }

    public final void setAttribute2(String value) {
        privateAttribute2 = value;
    }

    private String privateAttribute3;

    public final String getAttribute3() {
        return privateAttribute3;
    }

    public final void setAttribute3(String value) {
        privateAttribute3 = value;
    }

    private String privateAttribute4;

    public final String getAttribute4() {
        return privateAttribute4;
    }

    public final void setAttribute4(String value) {
        privateAttribute4 = value;
    }

    private String privateAttribute5;

    public final String getAttribute5() {
        return privateAttribute5;
    }

    public final void setAttribute5(String value) {
        privateAttribute5 = value;
    }

    private String privateAttribute6;

    public final String getAttribute6() {
        return privateAttribute6;
    }

    public final void setAttribute6(String value) {
        privateAttribute6 = value;
    }

    private String privateAttribute7;

    public final String getAttribute7() {
        return privateAttribute7;
    }

    public final void setAttribute7(String value) {
        privateAttribute7 = value;
    }

    private String privateAttribute8;

    public final String getAttribute8() {
        return privateAttribute8;
    }

    public final void setAttribute8(String value) {
        privateAttribute8 = value;
    }

    private String privateAttribute9;

    public final String getAttribute9() {
        return privateAttribute9;
    }

    public final void setAttribute9(String value) {
        privateAttribute9 = value;
    }

    private String privateAttribute10;

    public final String getAttribute10() {
        return privateAttribute10;
    }

    public final void setAttribute10(String value) {
        privateAttribute10 = value;
    }

    private String privateAttribute11;

    public final String getAttribute11() {
        return privateAttribute11;
    }

    public final void setAttribute11(String value) {
        privateAttribute11 = value;
    }

    private String privateAttribute12;

    public final String getAttribute12() {
        return privateAttribute12;
    }

    public final void setAttribute12(String value) {
        privateAttribute12 = value;
    }

    private String privateAttribute13;

    public final String getAttribute13() {
        return privateAttribute13;
    }

    public final void setAttribute13(String value) {
        privateAttribute13 = value;
    }

    private String privateAttribute14;

    public final String getAttribute14() {
        return privateAttribute14;
    }

    public final void setAttribute14(String value) {
        privateAttribute14 = value;
    }

    private String privateAttribute15;

    public final String getAttribute15() {
        return privateAttribute15;
    }

    public final void setAttribute15(String value) {
        privateAttribute15 = value;
    }
}
