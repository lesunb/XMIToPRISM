dtmc

module atest1
	satest1 : [-1..4] init 0;

	[A0_initial] satest1=1 -> 0.5:(satest1'=-1) + 0.5:(satest1'=3);
	[] satest1=0 -> 1.0:(satest1'=1);
	[] satest1=3 -> 0.5:(satest1'=-1) + 0.5:(satest1'=4);
	[A0_fail] satest1=-1 -> 1.0:(satest1'=-1);
	[A0_final] satest1=2 -> 1.0:(satest1'=2);
	[] satest1=4 -> 0.5:(satest1'=-1) + 0.5:(satest1'=2);
endmodule
