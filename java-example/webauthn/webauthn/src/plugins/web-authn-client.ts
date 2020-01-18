import axios from 'axios'
import { Plugin } from '@nuxt/types'
import WebAuthnClient from '~/plugins/types'

const client = axios.create({
    headers: {
        'Content-Type':'application/json'
    }
});

const WebAuthnClientInstsnce: Plugin = (context, inject) => {
    inject("attestationOptions", async <T>(email: string, displayName: string): Promise<T> => {
        try {
            const mail = email
            const name = displayName
            const options: T = await client.post<T>('/attestation/options', {
                'email': mail,
                'displayName': name
            }).then((response) => {
                return response.data;
            });
            return options
        } catch (err) {
            console.error(err)
            throw err
        }
    });
    inject("registerFinish", async <T>(credential: WebAuthnClient.AuthenticatorAttestationJSON): Promise<T> => {
        try {
            const response = await client.post<T>('/attestation/result', {
                'clientDataJSON': credential.clientDataJSON,
                'attestationObject': credential.attestationObject
            })
            .then((res) => res.data);
            return response;
        } catch (err) {
            console.error(err)
            throw err
        }
    });
    inject("assertionOptions", async <T>(email: string): Promise<T> => {
        try {
            const mail = email
            const options: T = await client.post<T>('/assertion/options', {
                'email': mail
            }).then((response) => {
                return response.data;
            });
            return options
        } catch (err) {
            console.error(err)
            throw err
        }
    });
    inject("authenticationFinish", async <T>(credential: WebAuthnClient.AuthenticatorAssertionJSON): Promise<T> => {
        try {
            const response = await client.post<T>('/assertion/result', {
                'clientDataJSON': credential.clientDataJSON,
                'credentialId': credential.credentialId,
                'authenticatorData': credential.authenticatorData,
                'signature': credential.signature
            })
            .then((res) => res.data);
            return response;
        } catch (err) {
            console.error(err)
            throw err
        }
    });
}

export default WebAuthnClientInstsnce
