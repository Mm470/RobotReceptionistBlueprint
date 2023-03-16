package furhatos.app.scenario10.flow
import furhatos.app.scenario10.nlu.*
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.Yes

// The Skill starts here
val Start : State = state(Interaction) {
// When a new user enters the scene
    onEntry {
        furhat.gesture(Gestures.BigSmile)
        furhat.say({
            +"Hello! Welcome to the national robotarium! Lovely to see you! I am Charlie!"
            +blocking {
                furhat.gesture(Gestures.BigSmile, async = true)
            }
            +"the receptionist! "})
        furhat.gesture(Gestures.BigSmile)
        furhat.say("it will be great if you can speak when my green light is on!")
        furhat.ledStrip.solid(java.awt.Color.GREEN)
        furhat.say("That's when I can hear you!")
        furhat.ledStrip.solid(java.awt.Color(0,0,0))
        if(furhat.isListening()){
            furhat.ledStrip.solid(java.awt.Color.GREEN)
        }
        goto(assistance())
    }


    onUserLeave (instant = false) {
        if(it == users.current){
            furhat.ledStrip.solid(java.awt.Color(0,0,0))
            furhat.ledStrip.solid(java.awt.Color.YELLOW)
            furhat.ledStrip.solid(java.awt.Color(0,0,0))
            furhat.say("Have a lovely day! Goodbye !")
        }
        goto(Interaction)
    }

}

fun assistance() : State = state(Interaction) {
    onEntry {
        furhat.ledStrip.solid(java.awt.Color(0,0,0))
        furhat.gesture(Gestures.BigSmile)
        furhat.say("How can I help you today ?")
        furhat.ledStrip.solid(java.awt.Color.GREEN)
        furhat.gesture(Gestures.Smile)
        furhat.listen()

    }

    onUserLeave (instant = false) {
        if(it ==users.current) {
            furhat.ledStrip.solid(java.awt.Color(0, 0, 0))
            furhat.ledStrip.solid(java.awt.Color.YELLOW)
            furhat.ledStrip.solid(java.awt.Color(0, 0, 0))
            furhat.say("Bye for now!")
        }
        goto(Interaction)
    }

    onResponse <Notsure> {
        furhat.ledStrip.solid(java.awt.Color(0,0,0))
        furhat.say("I am here if you need my help!")
        furhat.gesture(Gestures.Smile)
    }
    onResponse<Howareyou> {
        furhat.ledStrip.solid(java.awt.Color(0,0,0))
        furhat.gesture(Gestures.BigSmile)
        furhat.say("Fortunately! I am programmed to be always happy!")
        furhat.gesture(Gestures.BigSmile)
    }

    onResponse<Thanks> {
        furhat.ledStrip.solid(java.awt.Color(0,0,0))
        furhat.say("You are Welcome!")
        furhat.gesture(Gestures.BigSmile)
    }

    //


    onResponse<FindRoom> {
        val isLandmark = false
        var roomNumber = it.intent.getEntities().getValue("num").toString()

        if (roomNumber == "1") {
            roomNumber = "one"
        } else if (roomNumber == "2") {
            roomNumber = "two"
        } else if (roomNumber == "3") {
            roomNumber = "three"
        } else if (roomNumber == "4") {
            roomNumber = "four"
        } else if (roomNumber == "5") {
            roomNumber = "five"
        } else if (roomNumber == "6") {
            roomNumber = "six"
        } else if (roomNumber == "7") {
            roomNumber = "seven"
        }

        if (isLandmark) {
            val (dir, landmarkList) = extractorLandmark("reception", "room $roomNumber", debugg = false)
            furhat.say(landmarkGeneration(roomNumber, dir, landmarkList))
        } else {
            val dir = extractorRelative("reception", "room $roomNumber", debugg = false)
            furhat.say(relativeGeneration(roomNumber, dir))
        }




        // furhat.say("You can find the room ${room_num} by following the direction.")

        /*
        when (room_num)
        {
            "one" ->
            {
                furhat.say({
                    random{
                        "Start at the $ Reception area. Go forward . then $ dir ${if(isLandmark) "$ at the $ lm" else ""}. and Continue straight . and $ dir ${if(isLandmark) "$ at the $ lm" else ""}. You should now be facing Room 1."
                        +"Starting from the $ Reception area, move forward. ${if(isLandmark) "$ at the $ lm" else ""} $ dir then continue straight. ${if(isLandmark) "$ at the $ lm" else ""} $ dir. and you will be in Room 1."
                        +"Start at the $ Reception area. move straight . ${if(isLandmark) "$ at the $ lm" else ""} $ dir. Move forward . ${if(isLandmark) "$ at the $ lm" else ""} $ dir . You should now be in front of Room 1."
                    }
                })
            }
            "two" ->
            {
                furhat.say({
                    random{
                        "Start at $ Reception. move further. ${if(isLandmark) "$ at the $ lm" else ""} $ dir, move forward. ${if(isLandmark) "$ at the  $ lm" else ""} $ dir . you will be in room 2"
                        + "Start at the $ Reception area. Go straight. then $ dir ${if(isLandmark) "$ at the $ lm" else ""}. go forward . ${if(isLandmark) "$ at the $ lm" else ""} $ dir . You should find Room 2 infront of you"
                    }
                })
            }
            "three" ->
            {
                furhat.say({
                    random{
                        "Start at the $ Reception area. move forward . then  $ dir ${if(isLandmark) "$ at the $ lm" else ""}. go straight . $ dir ${if(isLandmark) "$ at the $ lm" else ""} . You should see Room 3 "
                        + "Start at $ Reception. go straight then . $ dir ${if(isLandmark) " $ at the $ lm" else ""}, go forward. ${if(isLandmark) " $ at the $ lm" else ""}  $ dir. Now you will be in room 3"
                    }
                })
            }
            "four" ->
            {
                furhat.say({
                    random{
                        "Start at the $ Reception area. Go straight.  ${if(isLandmark) " $ at the $ lm" else ""} move $ dir . You will be in Room 4."
                        + "Start at the $ Reception area. Go straight ahead then move $ dir ${if(isLandmark) "$ at the $ lm" else ""}. You should now be in front of Room 4."
                    }
                })
            }
            "five" ->
            {
                furhat.say({
                    random{
                        "Start at the $ Reception area move forward. then $ dir ${if(isLandmark) "$ at the $ lm" else ""}. then Continue straight . then $ dir ${if(isLandmark) "$ at the $ lm" else ""} . you will have reached Room 5."
                        + "Start at the $ Reception area. Go straight ahead. and $ dir ${if(isLandmark) "$ at the $ lm" else ""}. and Continue straight . then take $ dir ${if(isLandmark) "$ at the $ lm" else ""}. You should now be in front of Room 5."
                    }
                })
            }
            "six" ->
            {
                furhat.say({
                    random{
                        "Start at the $ Reception area. Go straight and move $ dir  ${if(isLandmark) "$ at the $ lm" else ""} . then $ dir ${if(isLandmark) "$ at the $ lm" else ""}. you will reach Room 6"
                    }
                })
            }
            "seven" ->
            {
                furhat.say({
                    random{
                        "Start at the $ Reception area. Go straight ahead . and move $ dir ${if (isLandmark) "$ at the $ fc " else ""}. then  $ dir ${if(isLandmark) "$ at the $ lm" else ""} . Now you would have reached Room 7"
                        + "Start at the $ Reception area. Go straight . and move $ dir ${if(isLandmark)"$ at the $ fc " else ""} . then $ dir ${if(isLandmark) "$ at the $ lm" else ""}. You should now be in front of Room 7."
                    }
                })
            }
        }*/

        furhat.say("Do you want me to repeat it?")
        furhat.listen()
    }

    onResponse<AskRepeat> {
//        furhat.say("Bazooka")
        furhat.gesture(Gestures.BigSmile)
        furhat.listen()
    }

    //

    onResponse { // Catches everything else

        furhat.ledStrip.solid(java.awt.Color(0,0,0))
        furhat.say({
            random {
                +" Sorry! I didn't hear you, Would you speak a little louder when my green light is on please"
                +" Sorry I didn't catch that! Can you speak up, whenever my green light is on please?"
                +"Can you please speak more loudly, when the green light is on"
                +" Is it possible for you to raise your voice, a little bit please! When my green light is on! Just so I can hear you clearly!"

            }
        })
        furhat.ledStrip.solid(java.awt.Color.GREEN)
        furhat.gesture(Gestures.Smile)
        furhat.listen()

    }
    onNoResponse { // Catches silence
        furhat.ledStrip.solid(java.awt.Color(0, 0, 0))
        furhat.say({
                random {
                    +" Sorry! I didn't hear you, Would you speak a little louder when my green light is on please"
                    +" Sorry I didn't catch that! Can you speak up, whenever my green light is on please?"
                    +"Can you please speak more loudly, when the green light is on"
                    +" Is it possible for you to raise your voice, a little bit please! When my green light is on! Just so I can hear you clearly!"
                }
            })
        furhat.ledStrip.solid(java.awt.Color.GREEN)
        furhat.gesture(Gestures.Smile)
        furhat.listen()
    }
}
