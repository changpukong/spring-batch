package yfu.practice.springbatch.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "LON_JOB_EXECUTION_CONTEXT")
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
	
//	@OneToOne(fetch = FetchType.LAZY)
//	@MapsId
//    @JoinColumn(name = "JOB_EXECUTION_ID")
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//	private BatchJobExecution batchJobExecution;

}