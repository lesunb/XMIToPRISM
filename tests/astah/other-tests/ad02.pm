dtmc

module ad02
	sad02 : [-1..4] init 0;

	[] sad02=4 -> 0.07:(sad02'=-1) + 0.93:(sad02'=2);
	[A0_fail] sad02=-1 -> 1.0:(sad02'=-1);
	[] sad02=3 -> 0.08:(sad02'=-1) + 0.92:(sad02'=4);
	[] sad02=0 -> 1.0:(sad02'=1);
	[A0_initial] sad02=1 -> 0.09:(sad02'=-1) + 0.91:(sad02'=3);
	[A0_final] sad02=2 -> 1.0:(sad02'=2);
endmodule
