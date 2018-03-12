/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hyphenate.easeui.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name="sht_person")
public class PersonInfo {
    /**
     * relname : Tom
     * companyname :
     * header : /uploads/20171101/150951511059f95f66685bf.jpg
     * phone : 18381079491
     * status : 1
     */
    @Column(name = "phone", isId = true)
    private String phone;
    @Column(name = "relname")
    private String relname;
    @Column(name = "companyname")
    private String companyname;
    @Column(name = "header")
    private String header;
    @Column(name = "uid")
    private String uid;
    @Column(name = "status")
    private int status;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRelname() {
        return relname;
    }

    public void setRelname(String relname) {
        this.relname = relname;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
