package com.kinopoiskApp.restAPI.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "careers")
@Data
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(max = 127)
    @Column(name = "career_name")
    private String name;


    @ManyToMany(mappedBy = "humanCareers", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Human> humans;


    @OneToMany(mappedBy = "career")
    @JsonBackReference
    Set<HumanRoleInFilm> humanRoles;

}
