package eu.carrade.amaury.instantupload.database;

import android.provider.BaseColumns;


public final class InstantUploadContract
{
    public InstantUploadContract() {}

    public static final class ServiceEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "services";

        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_PRIMARY = "primary";
        public static final String COLUMN_NAME_SETTINGS = "settings";
    }

    public static final class HistoryEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "history";

        public static final String COLUMN_NAME_SERVICE_ID = "service_id";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_URI = "uri";
    }
}
