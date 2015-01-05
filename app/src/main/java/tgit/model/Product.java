package tgit.model;

import java.util.Date;

/**
 * Created by liulixiang on 2015/1/5.
 * Description: 产品信息
 */
public class Product {
    private String id;

    private String orgCode;

    /**
     * 工序编码
     */
    private String workshopCode;

    /**
     * 工序名称
     */
    private String workshopName;

    /**
     * 产品编号
     */
    private String productNo;

    /**
     * 批次编号
     */
    private String batchNumber;

    /**
     * 物料编码
     */
    private String materialNumber;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 毛重
     */
    private String gross;

    /**
     * 皮重
     */
    private String tare;

    /**
     * 净重
     */
    private String suttle;

    /**
     * 货位编号
     */
    private String inLocatorId;

    /**
     * 货位编号
     */
    private String inLocatorCode;

    /**
     * 货位
     */
    private String inLocator;

    /**
     * 状态
     */
    private String temp;

    /**
     * 型号
     */
    private String model;

    /**
     * 规格
     */
    private String specification;

    /**
     * 产品日期
     */
    private String productDate;

    private String inSubinventoryId;

    private String inSubinventory;

    private String bussinessId;

    /**
     * 拆包前ID
     */
    private String originId;

    /**
     * 拆包前产品编号
     */
    private String originProductNo;

    /**
     * 是否拆包，如果拆包了则不能使用
     */
    private Boolean isSplitted;

    /**
     * 是否发运
     */
    private Boolean isDelivered;

    /**
     * 发运时间
     */
    private java.util.Date deliveryTime;

    /**
     * 是否被质计系统处理
     */
    private Integer isHandledByZJ;

    /**
     * 质计处理时间
     */
    private java.util.Date handleTime;

    /**
     * 是否打印
     */
    private Boolean isPrinted;

    /**
     * 打印次数
     */
    private Integer printedTimes;

    /**
     * 打印时间
     */
    private java.util.Date lastPrintedTime;

    /**
     * 创建时间
     */
    private java.util.Date createTime;

    private String createUserId;

    private String createUserName;

    private java.util.Date lastUpdateTime;

    private String lastUpdateUserId;

    private String lastUpdateUserName;

    private String attribute1;

    private String attribute2;

    private String attribute3;

    private String attribute4;

    private String attribute5;

    private String attribute6;

    private String attribute7;

    private String attribute8;

    private String attribute9;

    private String attribute10;

    private String attribute11;

    private String attribute12;

    private String attribute13;

    private String attribute14;

    private String attribute15;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getWorkshopCode() {
        return workshopCode;
    }

    public void setWorkshopCode(String workshopCode) {
        this.workshopCode = workshopCode;
    }

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        this.materialNumber = materialNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getGross() {
        return gross;
    }

    public void setGross(String gross) {
        this.gross = gross;
    }

    public String getTare() {
        return tare;
    }

    public void setTare(String tare) {
        this.tare = tare;
    }

    public String getSuttle() {
        return suttle;
    }

    public void setSuttle(String suttle) {
        this.suttle = suttle;
    }

    public String getInLocatorId() {
        return inLocatorId;
    }

    public void setInLocatorId(String inLocatorId) {
        this.inLocatorId = inLocatorId;
    }

    public String getInLocatorCode() {
        return inLocatorCode;
    }

    public void setInLocatorCode(String inLocatorCode) {
        this.inLocatorCode = inLocatorCode;
    }

    public String getInLocator() {
        return inLocator;
    }

    public void setInLocator(String inLocator) {
        this.inLocator = inLocator;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getInSubinventoryId() {
        return inSubinventoryId;
    }

    public void setInSubinventoryId(String inSubinventoryId) {
        this.inSubinventoryId = inSubinventoryId;
    }

    public String getInSubinventory() {
        return inSubinventory;
    }

    public void setInSubinventory(String inSubinventory) {
        this.inSubinventory = inSubinventory;
    }

    public String getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(String bussinessId) {
        this.bussinessId = bussinessId;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getOriginProductNo() {
        return originProductNo;
    }

    public void setOriginProductNo(String originProductNo) {
        this.originProductNo = originProductNo;
    }

    public Boolean getIsSplitted() {
        return isSplitted;
    }

    public void setIsSplitted(Boolean isSplitted) {
        this.isSplitted = isSplitted;
    }

    public Boolean getIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(Boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getIsHandledByZJ() {
        return isHandledByZJ;
    }

    public void setIsHandledByZJ(Integer isHandledByZJ) {
        this.isHandledByZJ = isHandledByZJ;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public Boolean getIsPrinted() {
        return isPrinted;
    }

    public void setIsPrinted(Boolean isPrinted) {
        this.isPrinted = isPrinted;
    }

    public Integer getPrintedTimes() {
        return printedTimes;
    }

    public void setPrintedTimes(Integer printedTimes) {
        this.printedTimes = printedTimes;
    }

    public Date getLastPrintedTime() {
        return lastPrintedTime;
    }

    public void setLastPrintedTime(Date lastPrintedTime) {
        this.lastPrintedTime = lastPrintedTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    public void setLastUpdateUserId(String lastUpdateUserId) {
        this.lastUpdateUserId = lastUpdateUserId;
    }

    public String getLastUpdateUserName() {
        return lastUpdateUserName;
    }

    public void setLastUpdateUserName(String lastUpdateUserName) {
        this.lastUpdateUserName = lastUpdateUserName;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    public String getAttribute6() {
        return attribute6;
    }

    public void setAttribute6(String attribute6) {
        this.attribute6 = attribute6;
    }

    public String getAttribute7() {
        return attribute7;
    }

    public void setAttribute7(String attribute7) {
        this.attribute7 = attribute7;
    }

    public String getAttribute8() {
        return attribute8;
    }

    public void setAttribute8(String attribute8) {
        this.attribute8 = attribute8;
    }

    public String getAttribute9() {
        return attribute9;
    }

    public void setAttribute9(String attribute9) {
        this.attribute9 = attribute9;
    }

    public String getAttribute10() {
        return attribute10;
    }

    public void setAttribute10(String attribute10) {
        this.attribute10 = attribute10;
    }

    public String getAttribute11() {
        return attribute11;
    }

    public void setAttribute11(String attribute11) {
        this.attribute11 = attribute11;
    }

    public String getAttribute12() {
        return attribute12;
    }

    public void setAttribute12(String attribute12) {
        this.attribute12 = attribute12;
    }

    public String getAttribute13() {
        return attribute13;
    }

    public void setAttribute13(String attribute13) {
        this.attribute13 = attribute13;
    }

    public String getAttribute14() {
        return attribute14;
    }

    public void setAttribute14(String attribute14) {
        this.attribute14 = attribute14;
    }

    public String getAttribute15() {
        return attribute15;
    }

    public void setAttribute15(String attribute15) {
        this.attribute15 = attribute15;
    }
}
