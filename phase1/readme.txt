 _________  ____  ____   ______
|  _   _  ||_   ||   _|.' ___  |
|_/ | | \_|  | |__| | / .'   \_|
    | |      |  __  | | |
   _| |_    _| |  | |_\ `.___.'\
  |_____|  |____||____|`.____ .'
------TecHConference V.1.0------

Hi, Elias. In order to get our program setup there are a couple things needed to be done.
A quick note before you do anything: we just want to make clear our intention with some of the
classes we implemented. There are some classes that aren't used in Phase 1 or anywhere in our code.
We had implemented them prior and decided to use them for Phase 2 as extended features since we don't
need them in Phase 1. As well, the second SignUpSystem method with different parameters (located in SignupSystem.java)
remains unused until Phase 2.

You will also notice that both the message system and the event system utilize a subsystem abstract class
to make their run methods extremely modular. However, we did not incorporate this into the authentication system
since we did not see the need to break it down, nor did we see the need in the future to add more subsystems to
it (at least with respect to the specs provided). AuthenticationSystem is more of a facade for signup
and login, and we use signup else for organizers to create speakers so it wouldn't match the idea of being
a "subsystem."

As well, kind of the same idea with our presenters, we didn't decide to abstract them, since (a)
they have various different methods, i.e. they worry about the formatting of how events look, messages,
etc.. so we couldn't really have one main "display" method since we wanted the formatting of the strings
to be done in the presenters and not elsewhere to follow single responsibility.
It also felt wrong to just pass strings everywhere to just have them system.out.println. We also went off how Lindsey described
presenters for Phase 1 on Piazza as just a collection of methods that would print out dialogues.
We did try to extend some UI with each other when there was commonality like display error from ErrorUI.

Also, when it comes to entering rooms into the system for organizers, we only enter the room names and the capacity
of the event at the time of scheduling. We didn't see the need for having other than just primitives for rooms in the spec
for phase1. We do maintain all the required checks in order for room, speaker, and capacity conflicts. We did notice however
the change in Phase 2 that implies the need for room entities and a room manager/system. In hindsight it would've been
better to implement those systems in Phase 1 for Phase 2, but they were unpredicted and as a result left out of our design.
We assumed that when scheduling the organizers would select capable rooms and their capacities which would correlate to
the room size. For instance how in acorn, section sizes (prior to covid) were relative to the lecture hall capacity.

Lastly,
For when organizers schedule the events, we leave it to them when they want to schedule between 00:00 - 23:59. We don't
restrict the times, as we assume they are competent enough schedule properly since they are organizers of the event.


Without further ado, these are the following steps required to run our program:
    1. Enter the coreUtil Package
    2. Open up Migration package
    3. Run ModelCreator (This class just rebuilds the ser files with default data) its not intended to be
            part of our actual "program" so please don't mark it as such. Its main is separated so we
            don't rebuild the data each time on run.
    4. Next, head to the Main Package in src, and run the main method for TechConferenceMain
    5. As seen in the ModelCreator Class there are three default users you can select to login in with.
            1. user1 pass1 - attendee
            2. user2 pass2 - speaker
            3. user3 pass3 - organizer
            ** You can also create your own attendee through sign up on run, or create a speaker from
            user3 when you log in. **
    6. In order for data to persist, you need to have a "clean termination".
        This means you need to exit our program through the dialogues of "exit" presented.
        Or, you can click the exit button found on the side of the terminal so that the runtime
        exits with a status code 0. Otherwise, the shutdown hooks won't properly run.
        We did this instead of constantly saving because we'd rather only do it once instead
        of constantly writing to the files after each small change.

        A case in which you want to use the exit button on the left side of the terminal is midway through signup.
        If you are in the process of signing up, there is not exit button until u log in so after u create your user,
        if u want to terminate before logging in, use the exit button on the left hand side of the terminal.

If anything is not working, please don't hesitate to reach out to our technical support line (group email us). 24/7 Service, always smiling
and willing to help the customer no matter the issue :) We also provide on-site support, however, you have to buy the
technician lunch.

Enjoy! ;)