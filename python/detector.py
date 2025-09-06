import time
import random
from datetime import datetime
from dataclasses import dataclass, asdict
import json

# =========================
# Event Data Class
# =========================
@dataclass
class Event:
    id: str
    type: str
    magnitude: float = None
    timestamp: float = None
    location: str = ""

    def __post_init__(self):
        if self.timestamp is None:
            self.timestamp = time.time()

    def to_json(self):
        return json.dumps(asdict(self), default=str)

# =========================
# Detector Logic
# =========================
class Detector:
    def __init__(self, max_log_lines=5):
        self.log_messages = []
        self.earthquake_log = []
        self.max_log_lines = max_log_lines

    def add_log_message(self, message: str):
        timestamp = datetime.now().strftime("%H:%M:%S")
        entry = f"{timestamp}: {message}"
        self.log_messages.insert(0, entry)
        self.log_messages = self.log_messages[:self.max_log_lines]

    def log_earthquake_event(self):
        magnitude = round(random.uniform(3.0, 7.0), 1)
        location = f"Simulated Location {random.randint(1,100)}"
        timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        event_message = f"EARTHQUAKE DETECTED: Magnitude {magnitude} at {location} on {timestamp}"

        self.add_log_message(event_message)
        self.earthquake_log.insert(0, event_message)
        self.earthquake_log = self.earthquake_log[:self.max_log_lines]

        # Create Event object
        event = Event(
            id=str(int(time.time() * 1000)),
            type="earthquake",
            magnitude=magnitude,
            location=location
        )
        return event

    def simulate_events(self, total=20, delay=2):
        for i in range(1, total + 1):
            self.add_log_message(f"System event: Processing data packet {i}")
            time.sleep(delay)
            if random.random() < 0.3:  # ~30% chance of earthquake
                event = self.log_earthquake_event()
                print(event.to_json())  # can save/send somewhere

# =========================
# Example Usage
# =========================
if __name__ == "__main__":
    detector = Detector()
    detector.simulate_events(total=10, delay=1)
