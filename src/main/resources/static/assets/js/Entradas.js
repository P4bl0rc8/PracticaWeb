function getPDF(){

    //Create a new var doc
    var doc = new jsPDF();
    // We'll make our own renderer to skip this editor
    var specialElementHandlers = {
        '#getPDF': function(element, renderer){
            return true;
        },
        '.controls': function(element, renderer){
            return true;
        }
    };

    doc.fromHTML($('.ticket1').get(0), 15, 15, {
        'width': 170,
        'elementHandlers': specialElementHandlers
    });

    doc.save('Entrada.pdf');
}