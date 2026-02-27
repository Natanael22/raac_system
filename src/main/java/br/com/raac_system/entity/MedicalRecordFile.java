package br.com.raac_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "medical_record_files")
public class MedicalRecordFile extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", nullable = false)
    private MedicalRecord record;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false, length = 500)
    private String fileUrl;

    @Column(length = 100)
    private String mimeType;

    private Long sizeBytes;
}
