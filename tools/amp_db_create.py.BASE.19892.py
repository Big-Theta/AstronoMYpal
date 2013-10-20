import sqlite3
import datetime

conn = sqlite3.connect('amp.db')

def create_db_tables():
    curs = conn.cursor()
    curs.execute("""
            CREATE TABLE "android_metadata" (
                "locale" TEXT DEFAULT 'en_US'
            )
    """)

    curs.execute("""
            INSERT INTO "android_metadata" VALUES ('en_US')
    """)

    curs.execute("""
            CREATE TABLE user(
                _id         INTEGER PRIMARY KEY AUTOINCREMENT,
                name        TEXT
            )
    """)

    curs.execute("""
            CREATE TABLE stellar_object(
                _id         INTEGER PRIMARY KEY AUTOINCREMENT,
                ngc_ic      TEXT,
                name        TEXT,
                description TEXT,
                type        TEXT,
                distance    REAL,
                size        TEXT,
                constellation   TEXT,
                magnitude   REAL,
                right_ascension TEXT,
                declination TEXT,
                img_name    TEXT
            )
    """)

    curs.execute("""
            CREATE TABLE docket(
                _id         INTEGER PRIMARY KEY AUTOINCREMENT,
                name        TEXT,
                observations    INTEGER
            )
    """)

    curs.execute("""
            CREATE TABLE docket_item(
                _id         INTEGER PRIMARY KEY AUTOINCREMENT,
                docket_id   INTEGER,
                stellar_object_id   INTEGER,
                FOREIGN KEY(docket_id) REFERENCES docket(_id),
                FOREIGN KEY(stellar_object_id) REFERENCES stellar_object(_id)
            )
    """)

    curs.execute("""
            CREATE TABLE telescope(
                _id         INTEGER PRIMARY KEY AUTOINCREMENT,
                name        TEXT,
                type        TEXT,
                manufacturer    TEXT,
                aperture_in REAL
            )
    """)

    curs.execute("""
            CREATE TABLE session(
                _id         INTEGER PRIMARY KEY AUTOINCREMENT,
                started     DATETIME,
                telescope_id    INTEGER,
                user_id     INTEGER,
                FOREIGN KEY(telescope_id) REFERENCES telescope(_id),
                FOREIGN KEY(user_id) REFERENCES user(_id)
            )
    """)

    curs.execute("""
            CREATE TABLE session_item(
                _id          INTEGER PRIMARY KEY AUTOINCREMENT,
                session_id  INTEGER,
                stellar_object_id   INTEGER,
                time        DATETIME,
                conditions  TEXT,
                description TEXT,
                zipcode     INTEGER,
                gps_latitude    REAL,
                gps_longitude   REAL,
                FOREIGN KEY(session_id) REFERENCES session(_id),
                FOREIGN KEY(stellar_object_id) REFERENCES stellar_object(_id)
            )
    """)

    conn.commit()


def populate_mock_entries():
    """Assumes that the database is empty."""
    curs = conn.cursor()
    queries = [
        ("INSERT INTO user VALUES(?, ?)", (None, "John Smith",)),
        ("""
            INSERT INTO stellar_object
                VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
         """,
         (None, "200000", "foobar", "foobar", "mock",
          9000.1, "10x20", None, None, None, None)),
        ("INSERT INTO docket VALUES(?, ?, ?)", (None, "mock", 1)),
        ("INSERT INTO docket_item VALUES (?, ?, ?)", (None, 1, 1)),
        ("INSERT INTO telescope VALUES (?, ?, ?, ?, ?)",
         (None, "My scope", "refractor", "Orion", 6.0)),
        ("INSERT INTO session VALUES (?, ?, ?, ?)",
         (None, datetime.datetime.now(), 1, 1)),
        ("INSERT INTO session_item VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
         (None, 1, 1, datetime.datetime.now(), "bad", "big", 83843, 123.4, 567.8))
    ]
    for query, values in queries:
        curs.execute(query, values)

    conn.commit()

if __name__ == '__main__':
    create_db_tables()
    populate_mock_entries()

