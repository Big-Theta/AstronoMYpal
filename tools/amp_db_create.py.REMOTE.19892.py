import sqlite3
import datetime

import caldwell_parser

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

def populate_caldwell_data(rows):
    curs = conn.cursor()

    # Insert stellar objects
    stellar_object = ("INSERT INTO stellar_object VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
    for (row) in rows:
        curs.execute(stellar_object, row)

    # Create Caldwell docket
    caldwell_docket = ("INSERT INTO docket VALUES(?, ?, ?)")
    values = [(None, "Caldwell Partial Docket", 70),
              (None, "Caldwell Complete Docket", 109)]
    for value in values:
        curs.execute(caldwell_docket, value)

    # Get Caldwell docket ids
    docket_ids = []
    for i in range(2):
        curs.execute("SELECT _id FROM docket WHERE name='{}'".format(values[i][1]))
        docket_ids.append(curs.fetchall()[0][0])

    # Create docket items
    caldwell_item = ("INSERT INTO docket_item VALUES (?, ?, ?)")
    
    item_ids = []
    for item in rows:
        curs.execute("SELECT _id FROM stellar_object WHERE ngc_ic='{}'".format(item[1]))
        item_ids.append(curs.fetchall()[0][0])

    for docket_id in docket_ids:
        for item_id in item_ids:
            curs.execute(caldwell_item, (None, int(docket_id), int(item_id)))
    conn.commit()


if __name__ == '__main__':
    create_db_tables()
    populate_caldwell_data(caldwell_parser.rows[1:])

