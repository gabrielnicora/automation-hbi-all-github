import type { AddressChangeData } from '../models/change-address';

const PROVINCE = 'CORRIENTES';

export function createRandomAddress(): AddressChangeData {
  const suffix = Date.now().toString().slice(-6);

  return {
    apartment: `A${suffix.slice(-2)}`,
    floor: String((Number(suffix.slice(-1)) % 9) + 1),
    locality: '',
    number: suffix,
    province: PROVINCE,
    street: `QA ${suffix}`,
  };
}
