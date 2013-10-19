import sqlite3
conn = sqlite3.connect('amp.db')

def create_db_tables():
    curs = conn.cursor()

    curs.execute("""
            CREATE TABLE user(
                id          INTEGER PRIMARY KEY AUTOINCREMENT,
                name        VARCHAR(100)
            )
    """)

    curs.execute("""
            CREATE TABLE stellar_object(
                id          INTEGER PRIMARY KEY AUTOINCREMENT,
                ngc         INTEGER,
                ic          INTEGER,
                name        VARCHAR(100),
                type        VARCHAR(100),
                distance    REAL,
                constellation   VARCHAR(100),
                magnitude   REAL
            )
    """)

    curs.execute("""
            CREATE TABLE docket(
                id          INTEGER PRIMARY KEY AUTOINCREMENT,
                name        TEXT,
                observations    INTEGER
            )
    """)

    curs.execute("""
            CREATE TABLE docket_item(
                id          INTEGER PRIMARY KEY AUTOINCREMENT,
                docket_id   INTEGER,
                stellar_object_id   INTEGER,
                FOREIGN KEY(docket_id) REFERENCES docket(id),
                FOREIGN KEY(stellar_object_id) REFERENCES stellar_object(id)
            )
    """)

    curs.execute("""
            CREATE TABLE telescope(
                id          INTEGER PRIMARY KEY AUTOINCREMENT,
                name        TEXT,
                type        TEXT,
                manufacturer    TEXT,
                aperture_in REAL
            )
    """)

    curs.execute("""
            CREATE TABLE session(
                id          INTEGER PRIMARY KEY AUTOINCREMENT,
                started     DATETIME,
                telescope_id    INTEGER,
                user_id     INTEGER,
                FOREIGN KEY(telescope_id) REFERENCES telescope(id),
                FOREIGN KEY(user_id) REFERENCES user(id)
            )
    """)

    curs.execute("""
            CREATE TABLE session_item(
                id          INTEGER PRIMARY KEY AUTOINCREMENT,
                session_id  INTEGER,
                stellar_object_id   INTEGER,
                time        DATETIME,
                conditions  TEXT,
                description TEXT,
                FOREIGN KEY(session_id) REFERENCES session(id),
                FOREIGN KEY(stellar_object_id) REFERENCES stellar_object(id)
            )
    """)

    conn.commit()

