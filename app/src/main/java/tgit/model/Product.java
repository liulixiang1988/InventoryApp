package tgit.model;

/**
 * Created by liulixiang on 2014/12/25.
 */
public class Product {
    private String id;
    private String orgCode;
    private String workshopCode;
    private String workshopName;
    private String productNo;
    private String batchNumber;
    private String materialNumber;
    private String productName;
    private String gross;
    private String tare;
    private String suttle;
    private String inLocatorId;
    private String inLocatorCode;
    private String inLocator;
    private String temp;
    private String model;
    private String specification;
    private String productDate;
    private String inSubinventoryId;
    private String inSubinventory;
    private String isSplitted;

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

    public String getIsSplitted() {
        return isSplitted;
    }

    public void setIsSplitted(String isSplitted) {
        this.isSplitted = isSplitted;
    }

    @Override
    public String toString() {
        return productName+":"+inLocator;
    }
}
