package yfu.practice.springbatch.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "LON_JOB_EXECUTION_PARAMS")
@Data
public class BatchJobExecutionParams implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "JOB_EXECUTION_ID")
	private Long jobExecutionId;
	
	@Column(name = "TYPE_CD")
	private String typeCd;
	
	@Column(name = "KEY_NAME")
	private String keyName;
	
	@Column(name = "STRING_VAL")
	private String stringVal;
	
	@Column(name = "DATE_VAL")
	private Timestamp dateVal;
	
	@Column(name = "LONG_VAL")
	private Long longVal;
	
	@Column(name = "DOUBLE_VAL")
	private Double doubleVal;
	
	@Column(name = "IDENTIFYING")
	private String identifying;
	
}