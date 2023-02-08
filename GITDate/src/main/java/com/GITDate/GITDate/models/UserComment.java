package com.GITDate.GITDate.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

    @Entity
    public class UserComment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
        @Lob
        @Type(type ="org.hibernate.type.TextType")
        private String body;
        private Date createdAt;

        protected UserComment() {
        }

        @ManyToOne
        private AppUser createdBy;

        public UserComment(String body, Date createdAt, AppUser createdBy) {
            this.body = body;
            this.createdAt = createdAt;
            this.createdBy = createdBy;
        }

        public Long getId() {return id;}

        public void setId(Long id) {this.id = id;}

        public String getBody() {return body;}

        public void setBody(String body) {this.body = body;}

        public Date getCreatedAt() {return createdAt;}

        public void setCreatedAt(Date createdAt) {this.createdAt = createdAt;}

        public AppUser getCreatedBy() {return createdBy;}

        public void setCreatedBy(AppUser createdBy) {this.createdBy = createdBy;}
    }
