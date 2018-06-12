package beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ItemDataBeans implements Serializable {
	private int id;
	private String name;
	private String detail;
	private int price;
	private String fileName;
	private int totalPrice;
	private Date buyDate;
	private String deliveryMethodName;
	private int deliveryMethodPrice;

	public ItemDataBeans(int setTotalPrice, Date setBuyDate, String setDeliveryMethodName, int setDeliveryMethodPrice) {
		this.totalPrice = setTotalPrice;
		this.buyDate = setBuyDate;
		this.deliveryMethodName = setDeliveryMethodName;
		this.deliveryMethodPrice = setDeliveryMethodPrice;
	}

	public ItemDataBeans(int setId, String setName, int setPrice) {
		this.id = setId;
		this.name = setName;
		this.price = setPrice;
	}

	public ItemDataBeans(String setName, int setPrice) {
		this.name = setName;
		this.price = setPrice;
	}

	public ItemDataBeans() {
	}

	public int getId() {
		return id;
	}
	public void setId(int itemId) {
		this.id = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String itemName) {
		this.name = itemName;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int itemPrice) {
		this.price = itemPrice;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String filename) {
		this.fileName = filename;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Date getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public String getFormatDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH時mm分");
		return sdf.format(buyDate);
	}
	public String getDeliveryMethodName() {
		return deliveryMethodName;
	}
	public void setDeliveryMethodName(String deliveryMethodName) {
		this.deliveryMethodName = deliveryMethodName;
	}

	public int getDeliveryMethodPrice() {
		return deliveryMethodPrice;
	}

	public void setDeliveryMethodPrice(int deliveryMethodPrice) {
		this.deliveryMethodPrice = deliveryMethodPrice;
	}

}
