
findCapicua = function(){
    var allNumbers = db.phones.find().toArray();
    var arr=[]
    for(let i=0; i< allNumbers.length;i++){
        numb=allNumbers[i].display.split('-')[1]
        reversed = numb.split('').reverse().join(''); 
        if (numb == reversed) {
            print(numb, " is capicua")
            arr.push(numb)
        }   
    }
    print("number of capicuas:", arr.length)  
}