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
    public static final String FormGenVersion = URI_BASE + "FormGenVersionTag";
    public static final String p_assigned_version_tag = URI_BASE + "assignedFormGenVersionTag";
    public static final String p_version = URI_BASE + "formGenVersion";
    public static final String p_hashcode = URI_BASE + "formGenHashCode";
    public static final String p_contextUri = URI_BASE + "contextUri";
    public static final String p_connectionName = URI_BASE + "connectoinName";

    public static final String p_firstName = URI_BASE + "firstName";
    public static final String p_lastName = URI_BASE + "lastName";
    public static final String p_emailAddress = URI_BASE + "emailAddress";
    public static final String p_key = URI_BASE + "key";
}
