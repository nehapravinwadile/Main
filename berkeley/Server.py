from functools import reduce
from dateutil import parser
import threading
import datetime
import socket
import time

# Data structure to store client address and clock data
client_data = {}

# Nested thread function to receive clock time from a connected client
def startReceivingClockTime(connector, address):
    while True:
        try:
            # Receive clock time
            clock_time_string = connector.recv(1024).decode()
            clock_time = parser.parse(clock_time_string)
            clock_time_diff = datetime.datetime.now() - clock_time
            client_data[address] = {
                "clock_time": clock_time,
                "time_difference": clock_time_diff,
                "connector": connector
            }
            print("Client Data updated with:", str(address))
            time.sleep(5)
        except Exception as e:
            print(f"Error receiving clock time from {address}: {e}")
            break

# Master thread function to accept clients over a given port
def startConnecting(master_server):
    while True:
        try:
            # Accepting a client
            master_slave_connector, addr = master_server.accept()
            slave_address = f"{addr[0]}:{addr[1]}"
            print(f"{slave_address} connected successfully")
            current_thread = threading.Thread(
                target=startReceivingClockTime,
                args=(master_slave_connector, slave_address)
            )
            current_thread.start()
        except Exception as e:
            print(f"Error accepting client connection: {e}")
            break

# Function to fetch average clock difference
def getAverageClockDiff():
    if not client_data:
        return datetime.timedelta(0, 0)
    time_difference_list = [client['time_difference'] for client in client_data.values()]
    sum_of_clock_difference = sum(time_difference_list, datetime.timedelta(0, 0))
    average_clock_difference = sum_of_clock_difference / len(client_data)
    return average_clock_difference

# Function to generate cycles of clock synchronization
def synchronizeAllClocks():
    while True:
        print("New synchronization cycle started.")
        print("Number of clients to be synchronized:", len(client_data))
        if client_data:
            average_clock_difference = getAverageClockDiff()
            for client_addr, client in client_data.items():
                try:
                    synchronized_time = datetime.datetime.now() + average_clock_difference
                    client['connector'].send(str(synchronized_time).encode())
                except Exception as e:
                    print(f"Error sending synchronized time to {client_addr}: {e}")
        else:
            print("No client data. Synchronization not applicable.")
        print("\n")
        time.sleep(5)

# Function to initiate the Clock Server
def initiateClockServer(port=8080):
    try:
        master_server = socket.socket()
        master_server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        print("Socket at master node created successfully")
        master_server.bind(('', port))
        master_server.listen(10)
        print("Clock server started...")
        # Start making connections
        print("Starting to make connections...")
        master_thread = threading.Thread(target=startConnecting, args=(master_server,))
        master_thread.start()
        # Start synchronization
        print("Starting synchronization parallelly...")
        sync_thread = threading.Thread(target=synchronizeAllClocks)
        sync_thread.start()
    except Exception as e:
        print(f"Error initializing Clock Server: {e}")

# Driver function
if __name__ == '__main__':
    initiateClockServer(port=8080)


