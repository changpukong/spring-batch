package yfu.practice.springbatch.entity;

import java.io.Serializable;
import java.sql.Clob;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "LON_JOB_EXECUTION_PARAMS")
@Data
public class BatchJobExecutionContext implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "JOB_EXECUTION_ID")
	private Long jobExecutionId;
	
	@Column(name = "SHORT_CONTEXT")
	private String shortContext;
	
	@Column(name = "SERIALIZED_CONTEXT")
	private String serializedContext;
	
//	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "batchJobExecutionContext")
//	@PrimaryKeyJoinColumn
//    @JoinColumn(name = "JOB_EXECUTION_ID")
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//	private BatchJobExecution batchJobExecution;

}