package beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BuyDataBeans  implements Serializable {
	private int id;
	private int userId;
	private int totalPrice;
	private int delivertMethodId;
	private Date buyDate;

	private String deliveryMethodName;
	private int deliveryMethodPrice;
	private int boughtUserId;

	public BuyDataBeans(int setTotalPrice, Date setBuyDate, int setDeliveryMethodPrice, String setDeliveryMethodName) {
		this.totalPrice = setTotalPrice;
		this.buyDate = setBuyDate;
		this.deliveryMethodPrice = setDeliveryMethodPrice;
		this.deliveryMethodName = setDeliveryMethodName;
		}

	public BuyDataBeans(int setId, int setTotalPrice, Date setBuyDate, String setDeliveryMethodName,int setBoughtUserId) {
		this.id = setId;
		this.totalPrice = setTotalPrice;
		this.buyDate = setBuyDate;
		this.deliveryMethodName = setDeliveryMethodName;
		this.boughtUserId = setBoughtUserId;
	}

	public BuyDataBeans() {
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getDelivertMethodId() {
		return delivertMethodId;
	}
	public void setDelivertMethodId(int delivertMethodId) {
		this.delivertMethodId = delivertMethodId;
	}
	public Date getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	public String getDeliveryMethodName() {
		return deliveryMethodName;
	}
	public void setDeliveryMethodName(String deliveryMethodName) {
		this.deliveryMethodName = deliveryMethodName;
	}

	public String getFormatDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH時mm分");
		return sdf.format(buyDate);
	}
	public int getDeliveryMethodPrice() {
		return deliveryMethodPrice;
	}
	public void setDeliveryMethodPrice(int deliveryMethodPrice) {
		this.deliveryMethodPrice = deliveryMethodPrice;
	}

	public int getBoughtUserId() {
		return boughtUserId;
	}

	public void setBoughtUserId(int boughtUserId) {
		this.boughtUserId = boughtUserId;
	}

}
