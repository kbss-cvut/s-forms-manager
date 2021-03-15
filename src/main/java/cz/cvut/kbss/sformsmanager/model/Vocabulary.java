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
    public static final String p_versionName = URI_BASE + "versionName";
    public static final String p_save_hash = URI_BASE + "formGenSaveHashCode";
    public static final String p_formGen_created = URI_BASE + "formGenCreated";
    public static final String p_formGen_modified = URI_BASE + "formGenModified";
    public static final String p_hasRemoteContextURI = URI_BASE + "hasRemoteContextURI";
    public static final String p_synonym = URI_BASE + "synonym";
    public static final String p_connectionName = URI_BASE + "connectionName";

    public static final String Project = URI_BASE + "Project";
    public static final String p_repositoryUrl = URI_BASE + "repositoryUrl";
    public static final String p_serviceUrl = URI_BASE + "serviceUrl";
    public static final String p_appRepositoryUrl = URI_BASE + "appRepositoryUrl";
    public static final String p_key = URI_BASE + "key";

    public static final String ProjectContext = URI_BASE + "ProjectContext";

    // new
    public static final String QuestionTemplateSnapshot = URI_BASE + "QuestionTemplateSnapshot";
    public static final String QuestionTemplate = URI_BASE + "QuestionTemplate";
    public static final String QuestionTemplateVersion = URI_BASE + "QuestionTemplateVersion";
    public static final String p_hasQuestionTemplateSnapshot = URI_BASE + "p_hasQuestionTemplateSnapshot";

    public static final String FormTemplateSnapshot = URI_BASE + "FormTemplateSnapshot";
    public static final String FormTemplate = URI_BASE + "FormTemplate";
    public static final String FormTemplateVersion = URI_BASE + "FormTemplateVersion";

    public static final String SubmittedAnswer = URI_BASE + "SubmittedAnswer";

    public static final String Record = URI_BASE + "RecordSnapshot";
    public static final String RecordSnapshot = URI_BASE + "RecordSnapshot";
    public static final String RecordVersion = URI_BASE + "RecordVersion";
    public static final String p_recordCreated = URI_BASE + "recordCreated";
    public static final String p_recordSnapshotCreated = URI_BASE + "recordSnapshotCreated";
    public static final String p_hasRecordVersions = URI_BASE + "hasRecordVersions";

    public static final String p_originPathsHash = URI_BASE + "questionOriginPathHash";
    public static final String p_originPath = URI_BASE + "questionOriginPath";
    public static final String p_hasQuestionTemplateSnapshots = URI_BASE + "hasQuestionTemplateSnapshots";
    public static final String p_hasFormTemplate = URI_BASE + "hasFormTemplate";
    public static final String p_hasFormTemplateSnapshots = URI_BASE + "hasFormTemplateSnapshots";
    public static final String p_hasFormTemplateVersion = URI_BASE + "hasFormTemplateVersion";
    public static final String p_hasFormTemplateVersions = URI_BASE + "hasFormTemplateVersions";
    public static final String p_hasFormTemplateVersionKey = URI_BASE + "hasFormTemplateVersionKey";
    public static final String p_hasRecordSnapshots = URI_BASE + "hasRecordSnapshots";
    public static final String p_hasRecordSnapshot = URI_BASE + "hasRecordSnapshot";
    public static final String p_hasRecord = URI_BASE + "hasRecord";
    public static final String p_hasRecordVersion = URI_BASE + "hasRecordVersion";
    public static final String p_hasSubmittedAnswers = URI_BASE + "hasSubmittedAnswers";
    public static final String p_hasAnswerValue = URI_BASE + "hasAnswerValue";
    public static final String p_hasSubQuestionTemplates = URI_BASE + "hasSubQuestions";
    public static final String p_hasSubmittedAnswersHash = URI_BASE + "hasSubmittedAnswersHash";

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
