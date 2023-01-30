package ex01;

import java.sql.Date;

public class FlowerVo {
	private String id;
	private String item;
	private int quantity;
	private Date purchaseDate;
	private String seq_id;

	public FlowerVo() {
		super();
	}

	public FlowerVo(String id, String item, int quantity, Date purchaseDate, String seq_id) {
		super();
		this.id = id;
		this.item = item;
		this.quantity = quantity;
		this.purchaseDate = purchaseDate;
		this.seq_id = seq_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getSeq_id() {
		return seq_id;
	}

	public void setSeq_id(String seq_id) {
		this.seq_id = seq_id;
	}

	@Override
	public String toString() {
		return "FlowerVo [id=" + id + ", item=" + item + ", quantity=" + quantity + ", purchaseDate=" + purchaseDate
				+ ", seq_id=" + seq_id + "]";
	}

}
