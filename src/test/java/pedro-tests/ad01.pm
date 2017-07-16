dtmc

module ad01
	sad01 : [-1..3] init 0;

	[A0_initial] sad01=1 -> 0.1:(sad01'=-1) + 0.9:(sad01'=3);
	[] sad01=3 -> 0.09:(sad01'=-1) + 0.91:(sad01'=2);
	[A0_fail] sad01=-1 -> 1.0:(sad01'=-1);
	[A0_final] sad01=2 -> 1.0:(sad01'=2);
	[] sad01=0 -> 1.0:(sad01'=1);
endmodule
