package yfu.practice.springbatch.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "LON_STEP_EXECUTION")
@Data
public class BatchStepExecution implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "STEP_EXECUTION_ID")
	private Long stepExecutionId;

	@Column(name = "VERSION")
	private Long version;

	@Column(name = "STEP_NAME")
	private String stepName;

	@Column(name = "START_TIME")
	private Timestamp startTime;

	@Column(name = "END_TIME")
	private Timestamp endTime;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "COMMIT_COUNT")
	private Long commitCount;

	@Column(name = "READ_COUNT")
	private Long readCount;

	@Column(name = "FILTER_COUNT")
	private Long filterCount;

	@Column(name = "WRITE_COUNT")
	private Long writeCount;

	@Column(name = "READ_SKIP_COUNT")
	private Long readSkipCount;

	@Column(name = "WRITE_SKIP_COUNT")
	private Long writeSkipCount;

	@Column(name = "PROCESS_SKIP_COUNT")
	private Long processSkipCount;

	@Column(name = "ROLLBACK_COUNT")
	private Long rollbackCount;

	@Column(name = "EXIT_CODE")
	private String exitCode;

	@Column(name = "EXIT_MESSAGE")
	private String exitMessage;

	@Column(name = "LAST_UPDATED")
	private Timestamp lastUpdated;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "batchStepExecution")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private BatchStepExecutionContext batchStepExecutionContext;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "JOB_EXECUTION_ID")
    private BatchJobExecution batchJobExecution;
}