/*******************************************************************************
* ������ʶ: Copyright (C) 2007-2015 Socansoft.com ��Ȩ����
* ��������: SocanCode���������� V6.4.0.0 �Զ������� 2015/2/5 8:59:40
* 
* ��������: 
* 
* �޸ı�ʶ: 
* �޸�����: 
*******************************************************************************/

package tgit.model;

import java.sql.Timestamp;

public class Datalineinfo {
	private Integer id;
	private String weighRecordNumber;
	private String planCode;
	private String planNumber;
	private String batchNumber;
	private String supplierCode;
	private String supplierName;
	private String recipientCode;
	private String recipientName;
	private String materialCode;
	private String materialName;
	private String specification;
	private String model;
	private String carNumber;
	private String grossWeighHouseCode;
	private String grossWeighMachineCode;
	private Double grossWeight;
	private java.util.Date grossWeighTime;
	private String grossWeighManCode;
	private String grossWeighManName;
	private String grossWeighSupervisorCode;
	private String grossWeighSupervisor;
	private String grossWeighShift;
	private String tareWeighHouseCode;
	private String tareWeighMachineCode;
	private Double tareWeight;
	private java.util.Date tareWeighTime;
	private String tareWeighManCode;
	private String tareWeighManName;
	private String tareWeighSupervisorCode;
	private String tareWeighSupervisor;
	private String tareWeighShift;
	private Boolean isManualInputTare;
	private Double suttle;
	private String measureUnit;
	private java.util.Date weighTime;
	private Double deduction;
	private Double deductionPercent;
	private Double twiceDeduction;
	private Double twiceDeductionPercent;
	private Double moistureContent;
	private Boolean isManualInput;
	private Boolean isSurplus;
	private Boolean isReturnPurchase;
	private Integer operateBit;
	private Integer uploadBit;
	private java.util.Date uploadTime;
	private String createUserCode;
	private String createUserName;
	private java.util.Date createTime;
	private String lastModifiedUserCode;
	private String lastModifiedUserName;
	private java.util.Date lastModifiedTime;
	private String remark;
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
	private Integer opttype;
	private java.util.Date opttime;
	private String optdesc;

    public Integer getID() {
		return id;
	}
  
	public void setID(Integer id) {
		this.id = id;
	}

    public String getWeighRecordNumber() {
		return weighRecordNumber;
	}
  
	public void setWeighRecordNumber(String weighRecordNumber) {
		this.weighRecordNumber = weighRecordNumber;
	}

    public String getPlanCode() {
		return planCode;
	}
  
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

    public String getPlanNumber() {
		return planNumber;
	}
  
	public void setPlanNumber(String planNumber) {
		this.planNumber = planNumber;
	}

    public String getBatchNumber() {
		return batchNumber;
	}
  
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

    public String getSupplierCode() {
		return supplierCode;
	}
  
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

    public String getSupplierName() {
		return supplierName;
	}
  
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

    public String getRecipientCode() {
		return recipientCode;
	}
  
	public void setRecipientCode(String recipientCode) {
		this.recipientCode = recipientCode;
	}

    public String getRecipientName() {
		return recipientName;
	}
  
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

    public String getMaterialCode() {
		return materialCode;
	}
  
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

    public String getMaterialName() {
		return materialName;
	}
  
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

    public String getSpecification() {
		return specification;
	}
  
	public void setSpecification(String specification) {
		this.specification = specification;
	}

    public String getModel() {
		return model;
	}
  
	public void setModel(String model) {
		this.model = model;
	}

    public String getCarNumber() {
		return carNumber;
	}
  
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

    public String getGrossWeighHouseCode() {
		return grossWeighHouseCode;
	}
  
	public void setGrossWeighHouseCode(String grossWeighHouseCode) {
		this.grossWeighHouseCode = grossWeighHouseCode;
	}

    public String getGrossWeighMachineCode() {
		return grossWeighMachineCode;
	}
  
	public void setGrossWeighMachineCode(String grossWeighMachineCode) {
		this.grossWeighMachineCode = grossWeighMachineCode;
	}

    public Double getGrossWeight() {
		return grossWeight;
	}
  
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

    public java.util.Date getGrossWeighTime() {
		return grossWeighTime;
	}
  
	public void setGrossWeighTime(java.util.Date grossWeighTime) {
		this.grossWeighTime = grossWeighTime;
	}

    public String getGrossWeighManCode() {
		return grossWeighManCode;
	}
  
	public void setGrossWeighManCode(String grossWeighManCode) {
		this.grossWeighManCode = grossWeighManCode;
	}

    public String getGrossWeighManName() {
		return grossWeighManName;
	}
  
	public void setGrossWeighManName(String grossWeighManName) {
		this.grossWeighManName = grossWeighManName;
	}

    public String getGrossWeighSupervisorCode() {
		return grossWeighSupervisorCode;
	}
  
	public void setGrossWeighSupervisorCode(String grossWeighSupervisorCode) {
		this.grossWeighSupervisorCode = grossWeighSupervisorCode;
	}

    public String getGrossWeighSupervisor() {
		return grossWeighSupervisor;
	}
  
	public void setGrossWeighSupervisor(String grossWeighSupervisor) {
		this.grossWeighSupervisor = grossWeighSupervisor;
	}

    public String getGrossWeighShift() {
		return grossWeighShift;
	}
  
	public void setGrossWeighShift(String grossWeighShift) {
		this.grossWeighShift = grossWeighShift;
	}

    public String getTareWeighHouseCode() {
		return tareWeighHouseCode;
	}
  
	public void setTareWeighHouseCode(String tareWeighHouseCode) {
		this.tareWeighHouseCode = tareWeighHouseCode;
	}

    public String getTareWeighMachineCode() {
		return tareWeighMachineCode;
	}
  
	public void setTareWeighMachineCode(String tareWeighMachineCode) {
		this.tareWeighMachineCode = tareWeighMachineCode;
	}

    public Double getTareWeight() {
		return tareWeight;
	}
  
	public void setTareWeight(Double tareWeight) {
		this.tareWeight = tareWeight;
	}

    public java.util.Date getTareWeighTime() {
		return tareWeighTime;
	}
  
	public void setTareWeighTime(java.util.Date tareWeighTime) {
		this.tareWeighTime = tareWeighTime;
	}

    public String getTareWeighManCode() {
		return tareWeighManCode;
	}
  
	public void setTareWeighManCode(String tareWeighManCode) {
		this.tareWeighManCode = tareWeighManCode;
	}

    public String getTareWeighManName() {
		return tareWeighManName;
	}
  
	public void setTareWeighManName(String tareWeighManName) {
		this.tareWeighManName = tareWeighManName;
	}

    public String getTareWeighSupervisorCode() {
		return tareWeighSupervisorCode;
	}
  
	public void setTareWeighSupervisorCode(String tareWeighSupervisorCode) {
		this.tareWeighSupervisorCode = tareWeighSupervisorCode;
	}

    public String getTareWeighSupervisor() {
		return tareWeighSupervisor;
	}
  
	public void setTareWeighSupervisor(String tareWeighSupervisor) {
		this.tareWeighSupervisor = tareWeighSupervisor;
	}

    public String getTareWeighShift() {
		return tareWeighShift;
	}
  
	public void setTareWeighShift(String tareWeighShift) {
		this.tareWeighShift = tareWeighShift;
	}

    public Boolean getIsManualInputTare() {
		return isManualInputTare;
	}
  
	public void setIsManualInputTare(Boolean isManualInputTare) {
		this.isManualInputTare = isManualInputTare;
	}

    public Double getSuttle() {
		return suttle;
	}
  
	public void setSuttle(Double suttle) {
		this.suttle = suttle;
	}

    public String getMeasureUnit() {
		return measureUnit;
	}
  
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

    public java.util.Date getWeighTime() {
		return weighTime;
	}
  
	public void setWeighTime(Timestamp weighTime) {
		this.weighTime = weighTime;
	}

    public Double getDeduction() {
		return deduction;
	}
  
	public void setDeduction(Double deduction) {
		this.deduction = deduction;
	}

    public Double getDeductionPercent() {
		return deductionPercent;
	}
  
	public void setDeductionPercent(Double deductionPercent) {
		this.deductionPercent = deductionPercent;
	}

    public Double getTwiceDeduction() {
		return twiceDeduction;
	}
  
	public void setTwiceDeduction(Double twiceDeduction) {
		this.twiceDeduction = twiceDeduction;
	}

    public Double getTwiceDeductionPercent() {
		return twiceDeductionPercent;
	}
  
	public void setTwiceDeductionPercent(Double twiceDeductionPercent) {
		this.twiceDeductionPercent = twiceDeductionPercent;
	}

    public Double getMoistureContent() {
		return moistureContent;
	}
  
	public void setMoistureContent(Double moistureContent) {
		this.moistureContent = moistureContent;
	}

    public Boolean getIsManualInput() {
		return isManualInput;
	}
  
	public void setIsManualInput(Boolean isManualInput) {
		this.isManualInput = isManualInput;
	}

    public Boolean getIsSurplus() {
		return isSurplus;
	}
  
	public void setIsSurplus(Boolean isSurplus) {
		this.isSurplus = isSurplus;
	}

    public Boolean getIsReturnPurchase() {
		return isReturnPurchase;
	}
  
	public void setIsReturnPurchase(Boolean isReturnPurchase) {
		this.isReturnPurchase = isReturnPurchase;
	}

    public Integer getOperateBit() {
		return operateBit;
	}
  
	public void setOperateBit(Integer operateBit) {
		this.operateBit = operateBit;
	}

    public Integer getUploadBit() {
		return uploadBit;
	}
  
	public void setUploadBit(Integer uploadBit) {
		this.uploadBit = uploadBit;
	}

    public java.util.Date getUploadTime() {
		return uploadTime;
	}
  
	public void setUploadTime(java.util.Date uploadTime) {
		this.uploadTime = uploadTime;
	}

    public String getCreateUserCode() {
		return createUserCode;
	}
  
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

    public String getCreateUserName() {
		return createUserName;
	}
  
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

    public java.util.Date getCreateTime() {
		return createTime;
	}
  
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

    public String getLastModifiedUserCode() {
		return lastModifiedUserCode;
	}
  
	public void setLastModifiedUserCode(String lastModifiedUserCode) {
		this.lastModifiedUserCode = lastModifiedUserCode;
	}

    public String getLastModifiedUserName() {
		return lastModifiedUserName;
	}
  
	public void setLastModifiedUserName(String lastModifiedUserName) {
		this.lastModifiedUserName = lastModifiedUserName;
	}

    public java.util.Date getLastModifiedTime() {
		return lastModifiedTime;
	}
  
	public void setLastModifiedTime(java.util.Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

    public String getRemark() {
		return remark;
	}
  
	public void setRemark(String remark) {
		this.remark = remark;
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

    public Integer getOpttype() {
		return opttype;
	}
  
	public void setOpttype(Integer opttype) {
		this.opttype = opttype;
	}

    public java.util.Date getOpttime() {
		return opttime;
	}
  
	public void setOpttime(java.util.Date opttime) {
		this.opttime = opttime;
	}

    public String getOptdesc() {
		return optdesc;
	}
  
	public void setOptdesc(String optdesc) {
		this.optdesc = optdesc;
	}	
}