package yfu.practice.springbatch.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "LON_JOB_EXECUTION")
@Data
public class BatchJobExecution implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "JOB_EXECUTION_ID")
	private Long jobExecutionId;
	
	@Column(name = "VERSION")
	private Long version;
	
	@Column(name = "JOB_INSTANCE_ID")
	private Long jobInstanceId;
	
	@Column(name = "CREATE_TIME")
	private Timestamp createTime;
	
	@Column(name = "START_TIME")
	private Timestamp startTime;
	
	@Column(name = "END_TIME")
	private Timestamp endTime;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "EXIT_CODE")
	private String exitCode;
	
	@Column(name = "EXIT_MESSAGE")
	private String exitMessage;
	
	@Column(name = "LAST_UPDATED")
	private Timestamp lastUpdated;
	
	@Column(name = "JOB_CONFIGURATION_LOCATION")
	private String jobConfigurationLocation;
	
//	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "batchJobExecution")
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
    private BatchJobExecutionContext batchJobExecutionContext;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
    private BatchJobExecutionParams batchJobExecutionParams;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
    private BatchJobInstance batchJobInstance;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "batchJobExecution")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<BatchStepExecution> batchStepExecutionSet;
	
}