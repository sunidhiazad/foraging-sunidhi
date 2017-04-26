package entities;

import java.math.BigDecimal;

public class AngularRange {
	private BigDecimal from;
	private BigDecimal to;

	public AngularRange() {
		super();
	}

	public AngularRange(BigDecimal from, BigDecimal to) {
		super();
		this.from = from;
		this.to = to;
	}

	public BigDecimal getFrom() {
		return from;
	}

	public void setFrom(BigDecimal from) {
		this.from = from;
	}

	public BigDecimal getTo() {
		return to;
	}

	public void setTo(BigDecimal to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "[" + from + " - " + to + ")";
	}
}
