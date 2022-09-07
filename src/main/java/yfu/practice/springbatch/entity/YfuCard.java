package yfu.practice.springbatch.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "YFU_CARD")
@Data
public class YfuCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CARD_ID")
    private String cardId;

    @Column(name = "TYPE")
    private String type;
    
}