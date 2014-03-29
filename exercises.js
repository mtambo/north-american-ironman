function fizzBuzz() {
	for (var i=1; i<=100; i++) {
		var divisibleByFive = (i%5 == 0);
		var divisibleByThree = (i%3 == 0);
		if (divisibleByFive && divisibleByThree) {
			console.log(i+": FizzBuzz");
		} else if(divisibleByThree) {
			console.log(i+": Fizz");
		} else if(divisibleByFive) {
			console.log(i+": Buzz");
		} else {
			console.log(i);
		}
	}
} 


function fizzBuzzR(n) {
	if(n<=0) return;
	fizzBuzzR(n-1);
	var divisibleByFive = (n%5 == 0);
	var divisibleByThree = (n%3 == 0);
	if (divisibleByFive && divisibleByThree) {
		console.log(n+": FizzBuzz");
	} else if (divisibleByThree) {
		console.log(n+": Fizz");
	} else if (divisibleByFive) {
		console.log(n+": Buzz");
	} else {
		console.log(n);
	}
} 


function fib(n) {
	var prev=0, cur=1;
	for(var i=0; i<n; i++) {
		var next = cur+prev;
		console.log(i+": "+prev+"+"+cur+"="+cur);
		prev = cur;
		cur = next;
	}
}

function fibRecurse(n) {
	if(!window.called) window.called = 0;
	window.called++; 
	if(n<2) {
		console.log("return "+n);
		return n;
	} else {
		var x = fibRecurse(n-1) + fibRecurse(n-2);
		console.log("return "+x);
		return x;
	}
}


function bubbleSort(anArray) {
	for(var i=anArray.length-1; i>=0; i--) {
		for(var j=0; j<i; j++) {
			if(anArray[j] > anArray[j+1]) {
				var tmp = anArray[j];
				anArray[j]= anArray[j+1];
				anArray[j+1] = tmp;
			}	
		}
	}
	return anArray;
}


function bubbleSortR(anArray) {
	// base case, recurse on array until one element remains
	if(anArray.length == 1) return anArray;

	for(var j=0; j<anArray.length; j++) {
		if(anArray[j] > anArray[j+1]) {
			var tmp = anArray[j];	
			anArray[j]= anArray[j+1];
			anArray[j+1] = tmp;
		}	
	}
	return  bubbleSortR(anArray.slice(0, anArray.length-1)).concat([anArray[anArray.length-1]]);
}

function reverseString(string) {
	var reversed = "";
	for(var i=string.length-1; i>=0; i--) {
		reversed += string.charAt(i);
	}	
	return reversed;
}

function reverseStringR(string) {
	if(string.length <= 1) return string;
	return string.charAt(string.length-1) + reverseStringR(string.substring(0, string.length-1));
}


// linked-list implementation
function Node() {
	this.val = null;
	this.next = null;
}


var first = new Node();
var n = first;
for(var i=0; i<10; i++) {
	n.val = i;
	n.next = new Node();
	n = n.next;
}

n = first;
while(n) {
	console.log(n.val);
	n = n.next;
}



