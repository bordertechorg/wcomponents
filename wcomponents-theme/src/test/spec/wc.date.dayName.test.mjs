import dayName from "wc/date/dayName.mjs";
describe("wc/date/dayName", function() {
	let mondayWeek,
		defaultWeek;

	const tz = process.env.TZ;

	beforeAll(() => {
		// PLEASE LEAVE THE TIMEZONE SWITCH IN PLACE even if you don't understand why
		process.env.TZ = "America/Los_Angeles";
	});

	afterAll(() => {
		console.log("Resetting timezone to", tz);
	});

	beforeEach(() => {
		defaultWeek = dayName.get();
		mondayWeek = dayName.get(true);
	});

	it("testDaySunday", function() {
		expect(defaultWeek[0]).toBe("Sunday");
		expect(mondayWeek[0]).toBe("Monday");
	});

	it("testDayMonday", function() {
		expect(defaultWeek[1]).toBe("Monday");
		expect(mondayWeek[1]).toBe("Tuesday");
	});

	it("testDayTuesday", function() {
		expect(defaultWeek[2]).toBe("Tuesday");
		expect(mondayWeek[2]).toBe("Wednesday");
	});

	it("testDayWednesday", function() {
		expect(defaultWeek[3]).toBe("Wednesday");
		expect(mondayWeek[3]).toBe("Thursday");
	});

	it("testDayThursday", function() {
		expect(defaultWeek[4]).toBe("Thursday");
		expect(mondayWeek[4]).toBe("Friday");
	});

	it("testDayFriday", function() {
		expect(defaultWeek[5]).toBe("Friday");
		expect(mondayWeek[5]).toBe("Saturday");
	});

	it("testDaySaturday", function() {
		expect(defaultWeek[6]).toBe("Saturday");
		expect(mondayWeek[6]).toBe("Sunday");
	});
});
