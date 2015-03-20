package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;

@Entity
@NamedQueries({
@NamedQuery(name="Visit.GetAll", query="SELECT v FROM Visit v"),
@NamedQuery(name="Visit.CountAll", query="SELECT count(v) FROM Visit v"),
@NamedQuery(name="Visit.CountPerPatient", query="SELECT count(v) FROM Visit v WHERE v.activity.patienthistory.patient =:patient"),
@NamedQuery(name="Visit.GetAllPerPatient", query="SELECT v FROM Visit v WHERE v.activity.patienthistory.patient =:patient"),
@NamedQuery(name="Visit.CountPerActivity", query="SELECT count(v) FROM Visit v WHERE v.activity =:activity"),
@NamedQuery(name="Visit.GetAllPerActivity", query="SELECT v FROM Visit v WHERE v.activity =:activity")
})
public class Visit extends DBEntity<Integer> implements Serializable {

	private static final long serialVersionUID = 1496350686877985070L;
	
    public static final String COUNT_PER_PATIENT="Visit.CountPerPatient";
	public static final String GETALL_PER_PATIENT="Visit.GetAllPerPatient";
	public static final String COUNT_PER_ACTIVITY="Visit.CountPerActivity";
	public static final String GETALL_PER_ACTIVITY="Visit.GetAllPerActivity";
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private String comments;

	@NotNull
	private int color;

	@NotNull
	private String title;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date visitdate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date enddate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "activityid")
	private Activity activity;

	@NotNull
	private BigDecimal deposit;

	public Visit() {
	}

	public Date getVisitdate() {
		return this.visitdate;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public String getComments() {
		return this.comments;
	}

	public Integer getId() {
		return this.id;
	}

	public Activity getActivity() {
		return this.activity;
	}

	public BigDecimal getDeposit() {
		return this.deposit;
	}

	public String getTitle() {
		return this.title;
	}

	public Integer getColor() {
		return this.color;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public void setVisitdate(Date visitdate) {
		this.visitdate = visitdate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	@Override
	public String getXML() {
		StringBuilder ans = new StringBuilder("<visit></visit>");
		ans.insert(ans.indexOf("</visit"), "<start>" + visitdate + "</start>");
		ans.insert(ans.indexOf("</visit"), "<end>" + enddate + "</end>");
		ans.insert(ans.indexOf("</visit"), "<title>" + title + "</title>");
		ans.insert(ans.indexOf("</visit"), "<color>" + color + "</color>");
		ans.insert(ans.indexOf("</visit"), "<comments>" + comments
				+ "</comments>");
		return ans.toString();
	}

	@Override
	public String toString() {
		String ans = title+" ";

		SimpleDateFormat dftime = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat dfdate = new SimpleDateFormat("d/MMM");
		Calendar c = Calendar.getInstance();

		c.setTime(visitdate);
		int startday = c.get(Calendar.DAY_OF_MONTH);
		String starttime = dftime.format(visitdate);
		String startdate = dfdate.format(visitdate);

		c.setTime(enddate);
		int endday = c.get(Calendar.DAY_OF_MONTH);
		String endtime = dftime.format(enddate);
		String enddatestr = dfdate.format(enddate);

		if (startday == endday)
			return ans + startdate + " " + starttime + " - " + endtime;

		return ans + startdate + " " + starttime + " - " + enddatestr + " "
				+ endtime;
	}
}
