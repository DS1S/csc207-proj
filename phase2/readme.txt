 _________  ____  ____   ______
|  _   _  ||_   ||   _|.' ___  |
|_/ | | \_|  | |__| | / .'   \_|
    | |      |  __  | | |
   _| |_    _| |  | |_\ `.___.'\
  |_____|  |____||____|`.____ .'
------TecHConference V.1.0------


---------new------------
We ended up not going the MVP way and instead used Data Transfer Objects (DTO) as we were initially doing
as it felt more natural and is still consistent with clean architecture as described by uncle bob and on
numerous piazza posts.

We ended changing our directory structure to better encapsulate systems. You will notice that our main systems
are fully encapsulated to any systems that run under them. We also use factories in to get instances of these systems
as it further encapsulates our implementation. Meaning, if we wanted to swap our the EventSchedulerMenuSystem
with another class that performs an implementation regarded as a "scheduler" then it be easily replaced with no
difference to the main system.

We've tried to condense the smaller classes most notably the UI by removing one liners and setting up a inheritance
hierarchy instead. We've also collapsed all of the smaller event data input processors into a singular class. We maintain
the abstract input processor for singular input processors, whilst we leave the EventFieldsProcessor separate from it
as it deals with multiple input categories.

Also, when it comes to entering rooms into the system for organizers, we only enter the room names and the capacity
of the event at the time of scheduling. We didn't see the need for having other than just primitives for rooms. Since
it would be essentially redundant to ask the organizers to add rooms to the system just so they can choose the rooms
to put events. We have implemented it so when a organizer creates an event, they are putting the room name along
with the capacity of the room, that is the maximum capacity of the event. It would be pointless having another system
which would just be a "middle man/detour" to do this.

Features we have added:
    - Admin user who can delete messages of users, ban users, check user's messages, and check statistics

    - Multiple Conferences that have their own differing events with a shared user base. We implemented on the
        assumption that this application is owned by a singular company that runs each conference and so organizers
        of one conference are organizers of others

        As well, we assumed each conference took place on different days.

   -  We enhanced user messaging by allowing users to change the state of their messages to read, unread, and archived.
        As well, we allowed users to delete their messages. We really overhauled this system in terms of how messages
        are shown as before they were simply all shown at the same time; however, this wouldnt work on a read/unread
        basis. So we reworked it so instead when u view inbox it shows all your messages by title and allowing you
        to select a specific message you want to read.

   -  We allowed the user to print the Schedule/All events based on category: Time, Speaker, Signed Up for.
        Based on our previous assumption that conferences only last a day, the time option only allows for you
        to request the schedule by time for that particular day. However, since we do implement conferences on
        multiple days, you can go to other conferences and request the schedule by time for that conference on that day.

        We feel this is allowable as there was nothing in the spec indicating if conferences had to be held for more
        than a day.

   -  We added a statistics system for admins/organizers to utilize. Its found underneath the admin panel, since it
        is a privileged system. It has its own permission for it, and is no way associated to the admin themselves.
        The statistics system basically produces the following stats: User traffic for the past day, week and month,
        top 5 events, top 5 speakers, average number of attendees per event

   -  Our own feature we made was a linker for users underneath the social system. It allows users to add social media links
        to their profiles: i.e, linkedin, reddit, youtube, facebook, etc. All these links are pre-authorized to the best of
        our capabilities, meaning their are checked against a regex which matches a valid user profile for the specified
        site. Users, can then see the links of other users and decide to select them which opens that profile in their
        web browser (This may be dependent on OS how your desktop stores your preferred browser Will be shown in demo
        it does in fact work).


Design Patterns we use:
    - Factory
        We have multiple factories, specifically UserFactory, SocialSubSystemsFactory, and EventSubSystemFactory.

        UserFactory - implemented in UserFactory.java and used in UserManager.java, we utilize a factory here to
        encapsulate the process of creating an instance of a User and by delegating creation to its sub classes. As a
        result this makes our system even more Open and Closed as we not relying on a specific type of Users, but rather
        an abstraction. The UserFactory just provides a mean of encapsulating that creation and removing itself from
        being a responsibility in UserManager. Also since we are unsure what type of user is to be made until the method
        is called, the factory method allows us to instantiate that object based on the input.

        SocialSubSystemsFactory - implemented in SocialSubSystemsFactory.java used in SocialSystem.java. We utilize a
        factory here for more less further encapsulating the instantiation of systems used in the main system: Social.
        By calling the factory method to create our systems based on a string that returns an abstracted RunnableSystem,
        we are encapsulating the systems based on title and so if we ever wanted to change the class that actually
        implements say the functionality of Organizer's messaging, the Social System wouldn't have to worry about changing the class
        type in itself. It may seem pedantic but it betters the encapsulation process.

        Same idea for the EventSubSystemFactory

    - Dependency Injection

        These are pretty much everywhere that uses something like Lists (which is a service), but the most notable dependency injection
        is the use of dependency injection of for our WebOpener, WebAccessible. Since Web is layer 4 in Clean Architecture,
        we cannot have WebOpener (which performs opening a web browser so web interaction) be a dependency within a
        controller (linkerSystem). So what we do is we instead create a service called WebAccessible and we perform a dependency Injection
        in SocialSubSystemsFactory (the instantiation of the linker system) with WebOpener as a WebAccessible so that LinkerSystem can
        depend on a abstraction/service which inverts the dependency and allowing use to make calls to the service to a layer 4 object
        in layer 3.

    - Strategy

    InputProcessor is a type of strategy it performs processing of input and each individual strategy that extends InputProcessor
    in some shape performs the same process of gathering input data. Now although there is only one implementation/strategy
    its open to extension for more singular input processors. So we thought it be notable to mention it. InputProcessor
    and more specifically the strategy OptionInputProcessor is used in almost all menu system classes, one in MenuSystem itself
    for navigating menu options, as well as one in MessageMenuSystem, AdminMessageViewerSystem, EventSchedulerSystem, EventSignUpSystem.

    - Facade

    EventManager is a facade of EventScheduler, EventFilterer, and EventSignUp. We utilize a facade to maintain Single
    Responsibility of the EventManager as well to keep EventManager from getting too large. As well, we utilze a facade
    to encapsulate EventScheduler, EventFilterer, EventSignUp all within EventManager, those classes are only accesible
    through Event Manager.

    In the same light, you can view our systems also acting like Facades, for instance, SocialSystem, EventSystem, AdminSystem,
    and Authentication System all provide the "interface" of a run method which delegates the running of other systems. This
    provides a higher level of Single Responsibility since each system these main systems run perform different responsibilities.
    As well, it encapsulates the other systems from being run on their own and provides one main system for which all these
    other systems run.

What we've improved from phase 1:
    - Naming of our packages and renaming of sub systems to a more suitable name.
    - Fixed logic error for allowing non-speakers to be added as speakers to events.
    - Encapsulated our Systems completely to just the main systems.
    - Collapsed smaller classes into singular classes or found inheritance hierarchy
    - Made certain Systems more open close, i.e., messaging by adding statuses which can easily be extended with more.
    - We pushed some stuff from controllers into the use cases as it seemed more suitable. We still however, maintain
        our checks on Data Transfer Objects in the controller as it seems acceptable and in line with clean architecture.
        Since that data has to go to the presenter anyways, and also as noted on piazza it is totally fine to pass DTOs
        over from use case to controller instead of doing a MVP model.
    - We also changed how systems are instantiated, basically before you were able to pass in the number of options they
        had; however, this is bad and removes encapsulation, prone to error. So instead,
        Systems now instead declare themselves in their constructors how many options they have (including exit).
------------------------



Hi, Elias. In order to get our program setup there are a couple things needed to be done.

You will also notice that both the social system and the event system utilize a subsystem abstract class
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