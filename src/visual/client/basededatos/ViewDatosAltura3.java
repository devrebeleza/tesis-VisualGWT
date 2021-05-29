package visual.client.basededatos;

// Generated 20/12/2012 19:47:27 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ViewDatosAltura3 generated by hbm2java
 */
@Entity
@Table(name = "ViewDatosAltura3", catalog = "sgvdb")
public class ViewDatosAltura3 implements java.io.Serializable {

	private ViewDatosAltura3Id id;

	public ViewDatosAltura3() {
	}

	public ViewDatosAltura3(ViewDatosAltura3Id id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idDatosTodos", column = @Column(name = "idDatosTodos", nullable = false)),
			@AttributeOverride(name = "fecha", column = @Column(name = "fecha", nullable = false, length = 10)),
			@AttributeOverride(name = "hora", column = @Column(name = "hora", nullable = false, length = 8)),
			@AttributeOverride(name = "velPromAlt3", column = @Column(name = "velPromAlt3", precision = 5)),
			@AttributeOverride(name = "dirPromAlt3", column = @Column(name = "dirPromAlt3")),
			@AttributeOverride(name = "tempPromAlt3", column = @Column(name = "tempPromAlt3", precision = 5)),
			@AttributeOverride(name = "desvioAlt3", column = @Column(name = "desvioAlt3", precision = 5)),
			@AttributeOverride(name = "velMaxAlt3", column = @Column(name = "velMaxAlt3", precision = 5)),
			@AttributeOverride(name = "dirMaxAlt3", column = @Column(name = "dirMaxAlt3")),
			@AttributeOverride(name = "tempMaxAlt3", column = @Column(name = "tempMaxAlt3", precision = 5)),
			@AttributeOverride(name = "velMinAlt3", column = @Column(name = "velMinAlt3", precision = 5)),
			@AttributeOverride(name = "dirMinAlt3", column = @Column(name = "dirMinAlt3")),
			@AttributeOverride(name = "tempMinAlt3", column = @Column(name = "tempMinAlt3", precision = 5)) })
	public ViewDatosAltura3Id getId() {
		return this.id;
	}

	public void setId(ViewDatosAltura3Id id) {
		this.id = id;
	}

}
