function ValidationResult(errorFields){
    const validationResults = {};
    loadErrorFields(errorFields);
    
    this.createErrorFieldFor = function(id, errorCode){
        validationResults[id] = createErrorProcess(id, errorCode);
    }
    
    this.processResult = function(){
        for(let vindex in validationResults){
            validationResults[vindex]();
        }
    }
    
    function loadErrorFields(fields){
        for(let findex in fields){
            const errorField = fields[findex];
            validationResults[errorField] = createSuccessProcess(errorField);
        }
    }
}