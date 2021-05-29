package visual.client.basededatos;

// Generated 20/12/2012 19:47:27 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ViewDatosComunesId generated by hbm2java
 */
@Embeddable
public class ViewDatosComunesId implements java.io.Serializable {

	private int idDatosTodos;
	private Date fecha;
	private Date hora;
	private BigDecimal tempProm;
	private Integer humedad;
	private Integer presion;
	private BigDecimal tempMax;
	private BigDecimal tempMin;

	public ViewDatosComunesId() {
	}

	public ViewDatosComunesId(int idDatosTodos, Date fecha, Date hora) {
		this.idDatosTodos = idDatosTodos;
		this.fecha = fecha;
		this.hora = hora;
	}

	public ViewDatosComunesId(int idDatosTodos, Date fecha, Date hora,
			BigDecimal tempProm, Integer humedad, Integer presion,
			BigDecimal tempMax, BigDecimal tempMin) {
		this.idDatosTodos = idDatosTodos;
		this.fecha = fecha;
		this.hora = hora;
		this.tempProm = tempProm;
		this.humedad = humedad;
		this.presion = presion;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
	}

	@Column(name = "idDatosTodos", nullable = false)
	public int getIdDatosTodos() {
		return this.idDatosTodos;
	}

	public void setIdDatosTodos(int idDatosTodos) {
		this.idDatosTodos = idDatosTodos;
	}

	@Column(name = "fecha", nullable = false, length = 10)
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Column(name = "hora", nullable = false, length = 8)
	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	@Column(name = "tempProm", precision = 5)
	public BigDecimal getTempProm() {
		return this.tempProm;
	}

	public void setTempProm(BigDecimal tempProm) {
		this.tempProm = tempProm;
	}

	@Column(name = "humedad")
	public Integer getHumedad() {
		return this.humedad;
	}

	public void setHumedad(Integer humedad) {
		this.humedad = humedad;
	}

	@Column(name = "presion")
	public Integer getPresion() {
		return this.presion;
	}

	public void setPresion(Integer presion) {
		this.presion = presion;
	}

	@Column(name = "tempMax", precision = 5)
	public BigDecimal getTempMax() {
		return this.tempMax;
	}

	public void setTempMax(BigDecimal tempMax) {
		this.tempMax = tempMax;
	}

	@Column(name = "tempMin", precision = 5)
	public BigDecimal getTempMin() {
		return this.tempMin;
	}

	public void setTempMin(BigDecimal tempMin) {
		this.tempMin = tempMin;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ViewDatosComunesId))
			return false;
		ViewDatosComunesId castOther = (ViewDatosComunesId) other;

		return (this.getIdDatosTodos() == castOther.getIdDatosTodos())
				&& ((this.getFecha() == castOther.getFecha()) || (this
						.getFecha() != null && castOther.getFecha() != null && this
						.getFecha().equals(castOther.getFecha())))
				&& ((this.getHora() == castOther.getHora()) || (this.getHora() != null
						&& castOther.getHora() != null && this.getHora()
						.equals(castOther.getHora())))
				&& ((this.getTempProm() == castOther.getTempProm()) || (this
						.getTempProm() != null
						&& castOther.getTempProm() != null && this
						.getTempProm().equals(castOther.getTempProm())))
				&& ((this.getHumedad() == castOther.getHumedad()) || (this
						.getHumedad() != null && castOther.getHumedad() != null && this
						.getHumedad().equals(castOther.getHumedad())))
				&& ((this.getPresion() == castOther.getPresion()) || (this
						.getPresion() != null && castOther.getPresion() != null && this
						.getPresion().equals(castOther.getPresion())))
				&& ((this.getTempMax() == castOther.getTempMax()) || (this
						.getTempMax() != null && castOther.getTempMax() != null && this
						.getTempMax().equals(castOther.getTempMax())))
				&& ((this.getTempMin() == castOther.getTempMin()) || (this
						.getTempMin() != null && castOther.getTempMin() != null && this
						.getTempMin().equals(castOther.getTempMin())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdDatosTodos();
		result = 37 * result
				+ (getFecha() == null ? 0 : this.getFecha().hashCode());
		result = 37 * result
				+ (getHora() == null ? 0 : this.getHora().hashCode());
		result = 37 * result
				+ (getTempProm() == null ? 0 : this.getTempProm().hashCode());
		result = 37 * result
				+ (getHumedad() == null ? 0 : this.getHumedad().hashCode());
		result = 37 * result
				+ (getPresion() == null ? 0 : this.getPresion().hashCode());
		result = 37 * result
				+ (getTempMax() == null ? 0 : this.getTempMax().hashCode());
		result = 37 * result
				+ (getTempMin() == null ? 0 : this.getTempMin().hashCode());
		return result;
	}

}
