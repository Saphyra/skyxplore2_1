(function Register(){
    events.REGISTER_ATTEMPT = "register_attempt";
    events.BLOCK_REGISTRATION = "block_registration";
    events.ALLOW_REGISTRATION = "allow_registration";
    
    let registrationAllowed = false;
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.REGISTER_ATTEMPT},
        register
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.BLOCK_REGISTRATION},
        blockRegistration
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ALLOW_REGISTRATION},
        allowRegistration
    ));
    
    function blockRegistration(){
        registrationAllowed = false;
        document.getElementById("registration-button").disabled = true;
    }
    
    function allowRegistration(){
        registrationAllowed = true;
        document.getElementById("registration-button").disabled = false;
    }
    
    function register(){
        
    }
})();