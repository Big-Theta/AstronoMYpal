AstronoMYpal
============

Team: BigTheta
Contributors: Logan Evans, Cameron Evans, and Andrew Schwartzmeyer

This app was an attempt to make an astronomical observation log for
use in completing
[Astronomical League Observing Programs](http://www.astroleague.org/observing.html),
such as the
[Caldwell Program](http://www.astroleague.org/al/obsclubs/caldwell/cldwl.html)
(currently the only included dataset).

AstronoMYpal was programmed by the three of us over a period of ten
hours at the EECS WSU Hackathon on October 19, 2013.

Overall, the backend functionality exists for recording the viewing
sessions and getting achievements using a SQLite3 pre-loaded database
(for offline app use), but the front-end still needs work to display
everything.

It may go somewhere, it may not. We do want an app that is useful for
this, but it may just be treated as a prototype.

Future goals:

- Make user interface work
    - Record sessions
    - Review prior sessions
    - Add telescope
    - View objects, catalogues
    - Display the pictures/thumbnails
    - Night mode
    - See achievements
- Share with friends
    - Social networking integration
- More data
    - Setup an auto-scraper for all the catalogues
        - Probably prefer WikiPedia to AstroLeague
    - Use an API with cellular data
        - As opposed to wrestling with SQLite in Java
    - Maybe switch to an ORM (except the database is mostly programmed)
