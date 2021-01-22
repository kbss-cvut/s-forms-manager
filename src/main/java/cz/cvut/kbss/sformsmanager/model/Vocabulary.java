/**
 * Copyright (C) 2019 Czech Technical University in Prague
 * <p>
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.kbss.sformsmanager.model;

public class Vocabulary {

    public static final String URI_BASE = "http://onto.fel.cvut.cz/ontologies/sformsmanager#";

    public static final String FormGenMetadata = URI_BASE + "FormGenMetadata";
    public static final String FormGenVersion = URI_BASE + "FormGenVersion";
    public static final String FormGenInstance = URI_BASE + "FormGenInstance";
    public static final String p_assigned_version = URI_BASE + "assignedFormGenVersion";
    public static final String p_assigned_instance = URI_BASE + "assignedFormGenInstance";
    public static final String p_version = URI_BASE + "formGenVersion";
    public static final String p_save_hash = URI_BASE + "formGenSaveHashCode";
    public static final String p_formGen_created = URI_BASE + "formGenCreated";
    public static final String p_contextUri = URI_BASE + "contextUri";
    public static final String p_connectionName = URI_BASE + "connectionName";

    public static final String Connection = URI_BASE + "Connection";
    public static final String p_repositoryUrl = URI_BASE + "repositoryUrl";
    public static final String p_serviceUrl = URI_BASE + "serviceUrl";
    public static final String p_appRepositoryUrl = URI_BASE + "appRepositoryUrl";
    public static final String p_key = URI_BASE + "key";

    /* General */
    public final static String DOC_URI = "http://onto.fel.cvut.cz/ontologies/documentation/";
    public static final String Question = DOC_URI + "question";
    public final static String s_p_created = "http://purl.org/dc/terms/created";

    /* SForms */
    public final static String FORM_URI = "http://onto.fel.cvut.cz/ontologies/form/";
    public final static String s_p_has_question_origin = FORM_URI + "has-question-origin";

    /* Study Manager */
    public final static String VFN_STUDY_MANAGER_URI = "http://vfn.cz/ontologies/study-manager/"; // TODO: this is supposed to be configurable per connection, vfn is not project specific
    public static final String Patient = VFN_STUDY_MANAGER_URI + "patient-record";
}
