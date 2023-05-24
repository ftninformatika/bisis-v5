package com.ftninformatika.bisis.datawarehouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="task", schema = "bisis_reports", catalog = "bisis")
public class Task implements Serializable {
    @Id
    @SequenceGenerator(name="TASK_TASKID_GENERATOR", sequenceName="bisis_reports.TASK_TASK_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TASK_TASKID_GENERATOR")
    @Column(name="task_id")
    private Integer taskId;

    @Column(name="date")
    private LocalDateTime date;

    private String library;

    private Integer amount;

    @ManyToOne
    @JoinColumn(name="bibliographic_level_id")
    private BibliographicLevel bibliographicLevel;

    @ManyToOne
    @JoinColumn(name="record_id")
    private Record record;

    @ManyToOne
    @JoinColumn(name="action_id")
    private Action action;

    @ManyToOne
    @JoinColumn(name="librarian_id")
    private Librarian librarian;

    @ManyToOne
    @JoinColumn(name="record_type_id")
    private RecordType recordType;

    @ManyToOne
    @JoinColumn(name="serial_type_id")
    private SerialType serialType;

    @ManyToOne
    @JoinColumn(name="target_id")
    private Target target;

    @ManyToMany
    @JoinTable(
            name = "task_language", schema = "bisis_reports",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private Set<Language> languages =new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "task_udk", schema = "bisis_reports",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "udk_id"))
    private Set<Udk> udks =new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "task_content_type", schema = "bisis_reports",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "content_type_id"))
    private Set<ContentType> contentTypes =new HashSet<>();
}
