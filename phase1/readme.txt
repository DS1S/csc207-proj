Hi, Elias. In order to get our program setup there are a couple things needed to be done.
A quick note before you do anything, we just want to make clear our intention with some of the
classes we implemented. There are some classes that aren't used in phase1 or anywhere in our code,
we had implemented them prior and decided to use them for phase2 as extended features since we don't
need them in phase1.

Also, you'll notice that both message system and event system utilize a subsystem abstract class
to make their run method's extremely modular. We did not however incorporate this into authentication
since we didn't see the need to break it down, nor see the need in the future to add more subsystems to
auth. At least in respect to the specs provided. So AuthenticationSystem is more of a facade for signup
and login, and we use signup else for organizers to create speakers so it wouldn't match the idea of being
a "subsystem".

As well, kind of the same idea with our presenters, we didn't decide to abstract them, since (a)
they have various different methods. I.e., they worry about the formatting of how events look, messages,
etc.. so we couldn't really have one main "display" method since we wanted the formatting of the strings
to be done in the presenters and not else where to follow single responsibility.
and it kind of felt wrong to just pass strings everywhere to just have them system.out.println. We also went off how Lindsey described
presenters for phase 1 on piazza as just a collection of methods that would print out dialogues.
We did try to extend some UI with each other when there was commonality like display error from ErrorUI.

Also, when it comes to entering rooms into the system for organizers, we only enter the room names and the capacity
of the event at the time of scheduling. We didn't see the need for having other than just primitives for rooms in the spec
for phase1. We do maintain all the required checks in order for room, speaker, and capacity conflicts. We did notice however
the change in phase 2 that implies the need for room entities and a room manager/system. In hindsight it would've been
better to implement those systems in phase1 for phase2, but they were unpredicted and as a result left out of our design.
We assumed that when scheduling the organizers would select capable rooms and their capacities which would correlate to
the room size. For instance how in acorn, section sizes (prior to covid) were relative to the lecture hall capacity.

Lastly,
For when organizers schedule the events, we leave it to them when they want to schedule between 00:00 - 23:59. We don't
restrict the times, as we assume they are competent enough schedule properly since they are organizers of the event.


Without further ado, these are the following steps required to run our program.

Steps to run:
    1. Enter the coreUtil Package
    2. Open up Migration package
    3. Run ModelCreator (This class just rebuilds the ser files with default data) its not intended to be
            part of our actual "program" so please don't mark it as such. Its main is separated so we
            dont rebuild the data each time on run.
    4. Next, head to the Main Package in src, and run the main method for TechConferenceMain
    5. As seen in the ModelCreator Class there are three default users u can select to login in with.
            1. user1 pass1 - attendee
            2. user2 pass2 - speaker
            3. user3 pass3 - organizer
            ** you can also create your own attendee through sign up on run, or create a speaker from
            user3 when you login **

    6. In order for data to persist, you need to have a "clean termination".
        This means you need to exit our program through the dialogues of "exit" presented.
        Or, you can click the exit button found on the side of the terminal so that the runtime
        exits with a status code 0. Otherwise, the shutdown hooks won't properly run.
        We did this instead of constantly saving because we'd rather only do it once instead
        of constantly writing to the files after each small change.
