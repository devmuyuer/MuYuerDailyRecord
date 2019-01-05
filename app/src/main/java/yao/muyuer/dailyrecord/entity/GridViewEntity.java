package yao.muyuer.dailyrecord.entity;

import java.util.Date;

public class GridViewEntity {

	private int accountId;
	private String accountName;
	private int billId;
	private int bookId;
	private int amountType;
	private int amountId;
	private String amountTile;
	private double money;
	private int icon;
	private String content;
	private byte[] image;
	private Date addTime;

	private Integer[] imgs;
	private String[] types;
	private Boolean isSel;

	public GridViewEntity(Integer[] imgs, String[] types) {
		super();
		this.imgs = imgs;
		this.types = types;
		this.isSel = false;
	}

	public GridViewEntity() {
		super();
		this.isSel = false;
		// TODO Auto-generated constructor stub
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int accountId) {
		this.bookId = accountId;
	}

	public int getAmountType() {
		return amountType;
	}

	public void setAmountType(int amountType) {
		this.amountType = amountType;
	}

	public int getAmountId() {
		return amountId;
	}

	public void setAmountId(int amountId) {
		this.amountId = amountId;
	}

	public String getAmountTile() {
		return amountTile;
	}

	public void setAmountTile(String amountTile) {
		this.amountTile = amountTile;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}


	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer[] getImgs() {
		return imgs;
	}

	public void setImgs(Integer[] imgs) {
		this.imgs = imgs;
	}

	public String[] getTypes() {
		return types;
	}

	public void setTypes(String[] types) {
		this.types = types;
	}

	public Boolean getIsSel() {
		return isSel;
	}

	public void setIsSel(Boolean isSel) {
		this.isSel = isSel;
	}

}
