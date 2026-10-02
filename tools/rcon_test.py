"""Send commands to a local dev server over RCON and print the replies (used for smoke testing)."""
import socket, struct, sys, time

def packet(sock, req_id, ptype, body):
    data = struct.pack('<ii', req_id, ptype) + body.encode() + b'\x00\x00'
    sock.sendall(struct.pack('<i', len(data)) + data)
    length = struct.unpack('<i', recv_exact(sock, 4))[0]
    resp = recv_exact(sock, length)
    return resp[8:-2].decode(errors='replace')

def recv_exact(sock, n):
    buf = b''
    while len(buf) < n:
        chunk = sock.recv(n - len(buf))
        if not chunk:
            raise ConnectionError('closed')
        buf += chunk
    return buf

def main(cmd_file, password='iaf', port=25575):
    for _ in range(300):
        try:
            sock = socket.create_connection(('127.0.0.1', port), timeout=1200)
            break
        except OSError:
            time.sleep(2)
    else:
        print('could not connect'); return
    packet(sock, 1, 3, password)
    for i, line in enumerate(open(cmd_file).read().splitlines()):
        if not line.strip():
            continue
        if line.startswith('#sleep'):
            time.sleep(float(line.split()[1]))
            continue
        try:
            print(f'> {line}\n{packet(sock, i + 2, 2, line)}', flush=True)
        except Exception as e:
            print(f'> {line}\n!! {e}', flush=True)
            return

if __name__ == '__main__':
    main(sys.argv[1])
